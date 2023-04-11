package pl.edu.agh.db2.dronkashop.backend

import pl.edu.agh.db2.dronkashop.backend.ext.run
import pl.edu.agh.db2.dronkashop.backend.provider.DBProvider
import pl.edu.agh.db2.dronkashop.backend.provider.GraphQLProvider

fun main() {
    DBProvider.use { start() }
}

fun start() {
    val query = Resource.gets("/query/PersonQuery.graphql")
    val params = HashMap<String, Any>().also {
        it["pName"] = "Tom Hanks"
    }

    DBProvider.session().use { session ->
        val result = session.run(GraphQLProvider.translate(query, params))
        println(result.single()[0])
    }
}