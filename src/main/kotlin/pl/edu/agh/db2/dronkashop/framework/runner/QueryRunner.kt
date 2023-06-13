package pl.edu.agh.db2.dronkashop.framework.runner

import org.neo4j.driver.Result
import org.neo4j.graphql.Cypher
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider
import java.util.*

abstract class QueryRunner : AutoCloseable {
    abstract fun <R : Any> run(block: QueryRunner.() -> R): Optional<R>

    abstract fun run(query: String, params: Map<String, Any?>): Result

    fun run(cypher: Cypher) =
        run(cypher.query, cypher.params)

    fun runGraphQL(query: GraphQLQuery, params: Params) =
        run(GraphQLProvider.translate(query, params))
}