package pl.edu.agh.db2.dronkashop.framework.provider

import org.neo4j.driver.Value
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

typealias EntityClass = KClass<out Entity>

/**
 * Loads, stores and manages entities
 */
object EntityProvider {
    private val entities = HashMap<EntityClass, MutableList<Entity>>()

    /**
     * Returns entities of class `klass`
     */
    private fun getEntities(klass: EntityClass): MutableList<Entity> {
        if (entities.containsKey(klass))
            return entities[klass]!!

        entities[klass] = LinkedList<Entity>()
        return entities[klass]!!
    }

    private fun createEntity(klass: EntityClass): Entity =
        klass.createInstance().also { getEntities(klass).add(it) }

    /**
     * Returns cached Entity by its id, if Entity hasn't been
     * cached yet, then returns null
     */
    fun getEntityById(klass: EntityClass, id: ID): Entity? =
        getEntities(klass).find { item -> item.id == id }

    inline fun <reified T : Entity> getEntityById(id: ID): T? =
        getEntityById(T::class, id) as T?

    /**
     * Returns Entity of class `klass` with id `id`.
     * If Entity hasn't been pulled yet and `incomplete`
     * is set to `true`, then `Entity#pull()` will not be
     * called
     */
    fun entityById(
        klass: EntityClass,
        id: ID,
        incomplete: Boolean = false,
        runner: QueryRunner = DBProvider.defaultQueryRunner
    ): Entity {
        // if exists then return
        getEntityById(klass, id)?.also {
            return it
        }

        // otherwise create and return
        return createEntity(klass).also {
            it.id = id

            if (!incomplete) {
                it.pull(runner)
            }
        }
    }

    inline fun <reified T : Entity> entityById(
        id: ID,
        incomplete: Boolean = false,
        runner: QueryRunner = DBProvider.defaultQueryRunner
    ): T = entityById(T::class, id, incomplete, runner) as T

    /**
     * Checks whether entity with the specified id has been
     * already pulled or not
     */
    fun exists(klass: EntityClass, id: ID): Boolean =
        getEntityById(klass, id) != null

    inline fun <reified T : Entity> exists(id: ID): Boolean =
        exists(T::class, id)

    /**
     * Merges Entity of class `klass` with value `value`
     * Note: `value` must contain the `_id` key
     */
    fun merge(klass: EntityClass, value: Value): Entity =
        getEntityById(klass, ID(value["_id"].asInt()))?.also { entity ->
            entity.deserialize(value)
        } ?: createEntity(klass).apply { deserialize(value) }

    inline fun <reified T : Entity> merge(value: Value): T =
        merge(T::class, value) as T

    fun filter(klass: EntityClass, action: (Entity) -> Boolean): List<Entity> =
        getEntities(klass).filter(action)

    inline fun <reified T> filter(crossinline action: (T) -> Boolean): List<T> where T : Entity =
        filter(T::class) { entity -> action(entity as T) }.map { it as T }
}