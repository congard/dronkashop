package pl.edu.agh.db2.dronkashop.framework.entity

import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import kotlin.reflect.KClass

/**
 * Relation without properties, i.e. link to another Entity
 */
class Relation<T>(
    private val klass: KClass<T>
) where T : Entity {
    private var dst: T? = null

    fun isConfigured(): Boolean =
        dst != null

    @Suppress("UNCHECKED_CAST")
    fun configure(id: ID) {
        if (dst != null)
            throw RuntimeException("Relation cannot be configured more than once")
        dst = EntityProvider.entityById(klass, id, incomplete = true) as T
    }

    /**
     * Returns destination entity; loads it if incomplete
     */
    fun dst(): T {
        if (dst == null)
            throw RuntimeException("Relation hasn't been configured yet!")

        dst!!.also { entity ->
            if (entity.isIncomplete())
                entity.pull()
            return entity
        }
    }

    /**
     * Returns destination entity; without entity loading
     */
    fun dstId(): ID {
        if (dst == null)
            throw RuntimeException("Relation hasn't been configured yet!")
        return dst!!.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Relation<*>) return false

        return dst == other.dst
    }

    override fun hashCode(): Int {
        return dst?.hashCode() ?: ID.INVALID_VALUE
    }

    override fun toString(): String {
        return "Relation(dst=${dst?.id ?: "null"})"
    }

    companion object {
        inline fun <reified T : Entity> create(to: ID = ID.INVALID) =
            Relation(T::class).apply { if (to != ID.INVALID) configure(to) }
    }
}