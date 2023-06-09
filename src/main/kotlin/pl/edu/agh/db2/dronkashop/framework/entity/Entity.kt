package pl.edu.agh.db2.dronkashop.framework.entity

import org.neo4j.driver.Value
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.entity.annotations.Ignore
import pl.edu.agh.db2.dronkashop.framework.entity.exception.EmptyQueryException
import pl.edu.agh.db2.dronkashop.framework.entity.exception.EntityAlreadyExistsException
import pl.edu.agh.db2.dronkashop.framework.entity.exception.EntityException
import pl.edu.agh.db2.dronkashop.framework.entity.exception.UnexpectedResponseSizeException
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityClass
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import java.time.LocalDateTime
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

typealias EntityProperty = KMutableProperty<*>
typealias HashCode = Int

/**
 * All classes that you want to be mapped should inherit from this class
 */
abstract class Entity {
    private companion object {
        private const val POSTFIX_ADDED = "Added"
        private const val POSTFIX_DELETED = "Deleted"
    }

    private val propHashCodes = HashMap<EntityProperty, HashCode>()
    private var toManyRelations = HashMap<EntityProperty, List<ID>>() // TODO: Set instead of List?
    private var toOneRelations = HashMap<EntityProperty, HashCode>()

    protected abstract val updatePropertiesQuery: GraphQLQuery
    protected abstract val mutatePropertiesQuery: GraphQLQuery

    protected open val mutateRelationsQuery: GraphQLQuery = ""

    protected open val createNodeQuery: GraphQLQuery = ""

    var id = ID()
        set(value) {
            if (field.isValid() && field != value) {
                throw RuntimeException("You cannot change the ID once it has been set")
            } else {
                field = value
            }
        }

    /**
     * Creates new Entity in the database
     * Note: do not forget to call commit() if you are using TransactionQueryRunner
     * @throws EntityAlreadyExistsException if id is valid
     * @throws EmptyQueryException if `createNodeQuery` is empty
     * @throws EntityException if `Entity#persist` failed
     */
    fun persist(queryRunner: QueryRunner = DBProvider.defaultQueryRunner) {
        if (id.isValid())
            throw EntityAlreadyExistsException("Id is valid: $id")

        if (createNodeQuery.isEmpty())
            throw EmptyQueryException("Entity cannot be persisted: ${::createNodeQuery.name} is empty")

        val params = Params().also {
            forEachNodeProperty { property ->
                it[property.name] = property.getter.call(this)
            }
        }

        runAndDeserialize(createNodeQuery, params, queryRunner).ifPresentOrElse({
            // call merge to create relations if needed
            merge(queryRunner)
        }, {
            throw EntityException("Entity#persist failed: empty result")
        })
    }

    /**
     * Merges changes to the database
     * Note: do not forget to call commit() if you are using TransactionQueryRunner
     */
    fun merge(queryRunner: QueryRunner = DBProvider.defaultQueryRunner) {
        val mutatePropParams = getPropMutationParams()
        val mutateRelParams = getRelMutationParams()

        queryRunner.run {
            fun runAndDeserialize(query: GraphQLQuery, params: Params) {
                if (query.isEmpty()) {
                    System.err.println("Warning: empty query")
                    return
                }

                runAndDeserialize(query, params, this).orElseThrow {
                    EntityException("Entity#merge failed: empty result")
                }
            }

            if (mutatePropParams.size > 1) // if there are more params than just id
                runAndDeserialize(mutatePropertiesQuery, mutatePropParams)

            if (mutateRelParams.filter { it.value != null }.size > 1)
                runAndDeserialize(mutateRelationsQuery, mutateRelParams)
        }
    }

    /**
     * Saves changes to the database: if `id` is invalid, calls `Entity#persist`,
     * otherwise `Entity#merge` will be called.
     * Note: do not forget to call commit() if you are using TransactionQueryRunner
     */
    fun push(queryRunner: QueryRunner = DBProvider.defaultQueryRunner) {
        if (id.isValid()) {
            merge(queryRunner)
        } else {
            persist(queryRunner)
        }
    }

    /**
     * Fetches data from the database
     */
    fun pull(runner: QueryRunner = DBProvider.defaultQueryRunner) {
        val params = Params()
        params["id"] = id.value

        runAndDeserialize(updatePropertiesQuery, params, runner).orElseThrow {
            EntityException("Entity#pull failed")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun deserialize(record: Value) {
        val idType = ID::class.createType()

        forEachSerializableProperty({ property ->
            val propertyName = property.name.let { if (property.returnType.isSubtypeOf(idType)) "_id" else it }

            if (!record.containsKey(propertyName))
                return@forEachSerializableProperty

            val value = record[propertyName]

            val obj: Any = when (property.returnType.jvmErasure) {
                ID::class -> ID(value.asInt())
                Int::class -> value.asInt()
                Float::class -> value.asDouble().toFloat() // LossyCoercion workaround
                Boolean::class -> value.asBoolean()
                String::class -> value.asString()
                LocalDateTime::class -> value.asLocalDateTime()
                ToOneRelation::class -> {
                    val dstType = property.returnType.arguments[0].type!!
                    val dstClass = dstType.classifier as EntityClass

                    // Relation<dstType>
                    val relationClass = Relation::class.createType(
                        listOf(KTypeProjection.invariant(dstType))).classifier as KClass<Relation<Entity>>
                    val relation = relationClass.primaryConstructor!!.call(dstClass)

                    if (!value.isNull)
                        relation.configure(ID(value["_id"].asInt()))

                    relation
                }
                ToManyRelation::class -> {
                    val dstType = property.returnType.arguments[0].type!!
                    val dstClass = dstType.classifier as EntityClass

                    // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-type-projection/
                    // Relation<dstType>
                    val relationClass = Relation::class.createType(
                        listOf(KTypeProjection.invariant(dstType))).classifier as KClass<Relation<Entity>>

                    val result = ToManyRelation<Entity>()

                    for (dst in value.asList { it as Value }) {
                        val id = ID(dst["_id"].asInt())

                        val relation = relationClass.primaryConstructor!!.call(dstClass)
                        relation.configure(id)

                        result.add(relation)
                    }

                    result
                }
                else -> {
                    throw RuntimeException("Unsupported type ${property.returnType}")
                }
            }

            property.setter.call(this, obj)
        })

        updateState()
    }

    /**
     * Entity considered incomplete when Entity#deserialize hasn't been called
     */
    fun isIncomplete(): Boolean =
        propHashCodes.isEmpty()

    fun isModified(): Boolean {
        if (getChangedProperties().isNotEmpty())
            return true

        if (getChangedToOneRelations().isNotEmpty())
            return true

        val (oneToManyAdded, oneToManyRemoved) = getChangedToManyRelations()
        return oneToManyAdded.any { entry -> entry.value.isNotEmpty() } ||
                oneToManyRemoved.any { entry -> entry.value.isNotEmpty() }
    }

    protected fun runCustomMutation(
        mutation: GraphQLQuery,
        params: Params,
        runner: QueryRunner = DBProvider.defaultQueryRunner
    ) = runner.run {
        val result = runGraphQL(mutation, params.also { it["id"] = id.value })
        val records = result.list()

        if (records.size != 1)
            throw UnexpectedResponseSizeException(records.size)

        EntityProvider.merge(this@Entity::class, records[0][0])
    }

    private fun runAndDeserialize(query: GraphQLQuery, params: Params, runner: QueryRunner) = runner.run {
        val result = runGraphQL(query, params)
        val list = result.list()

        if (list.size != 1)
            throw UnexpectedResponseSizeException(list.size)

        deserialize(list[0][0])
    }

    /**
     * Constructs node properties mutation params
     */
    private fun getPropMutationParams(): Params {
        val params = Params()
        params["id"] = id.value

        getChangedProperties().forEach { property ->
            println(property.name)
            params[property.name] = property.getter.call(this) as Any
        }

        return params
    }

    /**
     * Constructs to-many & to-one mutation params
     */
    private fun getRelMutationParams(): Params {
        val params = Params()
        params["id"] = id.value

        forEachToManyRelation { property ->
            val name = property.name
            params["$name$POSTFIX_ADDED"] = null
            params["$name$POSTFIX_DELETED"] = null
        }

        forEachToOneRelation { property ->
            params[property.name] = null
        }

        getChangedToManyRelations().let { (added, removed) ->
            fun writeParams(changed: Map<EntityProperty, List<ID>>, postfix: String) {
                changed.forEach { (property, ids) ->
                    val name = "${property.name}$postfix"
                    params[name] = ids.map { it.value }
                }
            }

            writeParams(added, POSTFIX_ADDED)
            writeParams(removed, POSTFIX_DELETED)
        }

        getChangedToOneRelations().forEach { (property, _) ->
            params[property.name] = (property.getter.call(this) as ToOneRelation<*>).dstId().value
        }

        return params
    }

    private fun updateState() {
        updateHashCodes()
        updateToManyRelations()
        updateToOneRelations()
    }

    private fun updateHashCodes() {
        forEachNodeProperty { property ->
            propHashCodes[property] = property.getter.call(this).hashCode()
        }
    }

    private fun getChangedProperties(): List<EntityProperty> {
        val changed = LinkedList<EntityProperty>()

        forEachNodeProperty { property ->
            val hashCode = property.getter.call(this).hashCode()
            val oldHashCode = propHashCodes[property]

            if (hashCode != oldHashCode) {
                changed.add(property)
            }
        }

        return changed
    }

    private fun getToManyRelations() = run {
        val relations = toManyRelations::class.createInstance()

        forEachToManyRelation { property ->
            relations[property] =
                (property.getter.call(this) as ToManyRelation<*>).map { relation -> relation.dstId() }
        }

        relations
    }

    private fun updateToManyRelations() {
        toManyRelations = getToManyRelations()
    }

    private fun getChangedToManyRelations() = run {
        val current = getToManyRelations()
        val added = current.toMutableMap()

        for (entry in added) {
            added[entry.key] = entry.value - (toManyRelations[entry.key] ?: emptyList()).toSet()
        }

        val removed = toManyRelations.toMutableMap()

        for (entry in removed) {
            removed[entry.key] = entry.value - current[entry.key]!!.toSet()
        }

        added to removed
    }

    private fun getToOneRelations() = run {
        val relations = toOneRelations::class.createInstance()

        forEachToOneRelation { property ->
            relations[property] = (property.getter.call(this) as Relation<*>).hashCode()
        }

        relations
    }

    private fun updateToOneRelations() {
        toOneRelations = getToOneRelations()
    }

    private fun getChangedToOneRelations() = run {
        val current = getToOneRelations().map { it }
        current - toOneRelations.map { it }.toSet()
    }

    private fun isOneToOneRelation(property: EntityProperty): Boolean =
        (property.returnType.classifier as KClass<*>).isSubclassOf(ToOneRelation::class)

    private fun isOneToManyRelation(property: EntityProperty): Boolean =
        (property.returnType.classifier as KClass<*>).isSubclassOf(ToManyRelation::class)

    private fun isRelation(property: EntityProperty): Boolean =
        isOneToOneRelation(property) || isOneToManyRelation(property)

    /**
     * Returns node properties & relations
     */
    private fun forEachSerializableProperty(
        action: (EntityProperty) -> Unit,
        predicate: (EntityProperty) -> Boolean = { _ -> true }
    ) {
        this::class.memberProperties.forEach { property ->
            // skip query properties
            when (property.name) {
                ::updatePropertiesQuery.name,
                ::mutatePropertiesQuery.name,
                ::mutateRelationsQuery.name,
                ::createNodeQuery.name ->
                    return@forEach
            }

            // skip marked as ignore
            if (property.annotations.find { annotation -> annotation is Ignore } != null)
                return@forEach

            if (property !is EntityProperty)
                throw RuntimeException("Property ${property.name} is immutable")

            if (predicate(property)) {
                action(property)
            }
        }
    }

    /**
     * Allows to iterate over node properties
     */
    private fun forEachNodeProperty(action: (EntityProperty) -> Unit) =
        forEachSerializableProperty(action) { !isRelation(it) }

    /**
     * Allows to iterate over ToOne relations
     */
    private fun forEachToOneRelation(action: (EntityProperty) -> Unit) =
        forEachSerializableProperty(action) { isOneToOneRelation(it) }

    /**
     * Allows to iterate over ToMany relations
     */
    private fun forEachToManyRelation(action: (EntityProperty) -> Unit) =
        forEachSerializableProperty(action) { isOneToManyRelation(it) }

    /**
     * Uses reflection to construct a pretty string representation
     */
    override fun toString(): String {
        var string = "${this::class.simpleName} {"

        forEachSerializableProperty({ property ->
            string += "\n\t${property.name}: ${property.getter.call(this).toString()}"
        })

        return "$string\n}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Entity) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}