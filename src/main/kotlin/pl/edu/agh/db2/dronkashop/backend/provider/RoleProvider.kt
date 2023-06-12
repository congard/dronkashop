package pl.edu.agh.db2.dronkashop.backend.provider

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.backend.entity.Role
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.core.paramsOf
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import java.util.*

object RoleProvider {
    private val byNameQuery: GraphQLQuery =
        Resource.gets("/query/role/RoleGetByName.graphql")

    /**
     * If Role with the specified id hasn't been loaded yet,
     * it will be loaded, otherwise cached object will be returned
     */
    fun getById(id: ID, runner: QueryRunner = DBProvider.defaultQueryRunner): Role =
        EntityProvider.entityById(id, runner = runner)

    /**
     * If Role with the specified name has been already loaded,
     * it will be updated
     */
    fun getByName(name: String, runner: QueryRunner = DBProvider.defaultQueryRunner): List<Role> = runner.run {
        val resultRoles = LinkedList<Role>()

        val params = paramsOf("name" to name)
        val result = runGraphQL(byNameQuery, params)
        val records = result.list()

        for (record in records) {
            val value = record[0]
            EntityProvider.merge<Role>(value).also { resultRoles.add(it) }
        }

        resultRoles
    }.orElseThrow()
}