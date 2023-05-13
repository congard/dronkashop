package pl.edu.agh.db2.dronkashop.backend.provider

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.backend.entity.Category
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.ext.run
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider
import java.util.*

object CategoryProvider {

    private val byNameQuery: GraphQLQuery =
        Resource.gets("/query/category/CategoryGetByName.graphql")

    /**
     * If Category with the specified id hasn't been loaded yet,
     * it will be loaded, otherwise cached object will be returned
     */
    fun getById(id: ID): Category =
        EntityProvider.entityById(id)

    /**
     * If Category with the specified name has been already loaded,
     * it will be updated
     */
    fun getByName(name: String): List<Category> {
        val resultCategories = LinkedList<Category>()

        DBProvider.session().use { session ->
            val params = Params()
            params["name"] = name

            val result = session.run(GraphQLProvider.translate(CategoryProvider.byNameQuery, params))
            val records = result.list()

            for (record in records) {
                val value = record[0]
                EntityProvider.merge<Category>(value).also { resultCategories.add(it) }
            }
        }

        return resultCategories
    }
}