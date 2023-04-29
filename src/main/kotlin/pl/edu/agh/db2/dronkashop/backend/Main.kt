package pl.edu.agh.db2.dronkashop.backend

import pl.edu.agh.db2.dronkashop.backend.ext.run
import pl.edu.agh.db2.dronkashop.backend.provider.DBProvider
import pl.edu.agh.db2.dronkashop.backend.provider.GraphQLProvider

fun main() {
    DBProvider.use { start() }
}

fun start() {
    var query = Resource.gets("/query/allItems.graphql")
    var params = HashMap<String, Any>()

    DBProvider.session().use { session ->
        val result = session.run(GraphQLProvider.translate(query, params))
        result.list().forEach { x -> println(x) }
    }

    query = Resource.gets("/query/userOrders.graphql")
    params = HashMap<String, Any>().also {
        it["user"] = "janusz123"
    }

    DBProvider.session().use { session ->
        val result = session.run(GraphQLProvider.translate(query, params))
        result.list().forEach { x -> println(x) }
    }
}