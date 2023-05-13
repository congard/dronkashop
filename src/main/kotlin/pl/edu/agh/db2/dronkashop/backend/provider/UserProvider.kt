package pl.edu.agh.db2.dronkashop.backend.provider

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.backend.entity.User
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.ext.run
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider
import java.util.*

object UserProvider {

    private val byNameQuery: GraphQLQuery =
        Resource.gets("/query/user/UserGetByName.graphql")

    /**
     * If User with the specified id hasn't been loaded yet,
     * it will be loaded, otherwise cached object will be returned
     */
    fun getById(id: ID): User =
        EntityProvider.entityById(id)

    /**
     * If User with the specified name has been already loaded,
     * it will be updated
     */
    fun getByName(name: String): List<User> {
        val resultUsers = LinkedList<User>()

        DBProvider.session().use { session ->
            val params = Params()
            params["displayName"] = name

            val result = session.run(GraphQLProvider.translate(UserProvider.byNameQuery, params))
            val records = result.list()

            for (record in records) {
                val value = record[0]
                EntityProvider.merge<User>(value).also { resultUsers.add(it) }
            }
        }

        return resultUsers
    }
}