package pl.edu.agh.db2.dronkashop.backend.provider

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.backend.entity.Item
import pl.edu.agh.db2.dronkashop.framework.ext.run
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider.entityById
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider.merge
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider
import java.util.LinkedList

object ItemProvider {
    private val byNameQuery: GraphQLQuery =
        Resource.gets("/query/item/ItemGetByName.graphql")

    /**
     * If Item with the specified id hasn't been loaded yet,
     * it will be loaded, otherwise cached object will be returned
     */
    fun getById(id: ID): Item =
        entityById(id)

    /**
     * If Item with the specified name has been already loaded,
     * it will be updated
     */
    fun getByName(name: String): List<Item> {
        val resultItems = LinkedList<Item>()

        DBProvider.session().use { session ->
            val params = Params()
            params["name"] = name

            val result = session.run(GraphQLProvider.translate(byNameQuery, params))
            val records = result.list()

            for (record in records) {
                val value = record[0]
                merge<Item>(value).also { resultItems.add(it) }
            }
        }

        return resultItems
    }
}
