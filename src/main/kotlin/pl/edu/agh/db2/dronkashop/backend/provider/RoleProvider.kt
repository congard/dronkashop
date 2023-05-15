package pl.edu.agh.db2.dronkashop.backend.provider

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.backend.entity.Role
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import java.util.*

object RoleProvider {

    private val byNameQuery: GraphQLQuery =
        Resource.gets("/query/role/RoleGetByName.graphql")

    /**
     * If Role with the specified id hasn't been loaded yet,
     * it will be loaded, otherwise cached object will be returned
     */
    fun getById(id: ID): Role =
        EntityProvider.entityById(id)

    /**
     * If Role with the specified name has been already loaded,
     * it will be updated
     */
    fun getByName(name: String): List<Role> {
        val resultRoles = LinkedList<Role>()

        DBProvider.defaultQueryRunner.run {
            val params = Params()
            params["name"] = name

            val result = runGraphQL(byNameQuery, params)
            val records = result.list()

            for (record in records) {
                val value = record[0]
                EntityProvider.merge<Role>(value).also { resultRoles.add(it) }
            }
        }

        return resultRoles
    }
}