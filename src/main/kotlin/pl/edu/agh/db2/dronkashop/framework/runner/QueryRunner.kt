package pl.edu.agh.db2.dronkashop.framework.runner

import org.neo4j.driver.Result
import org.neo4j.graphql.Cypher
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider

abstract class QueryRunner : AutoCloseable {
    abstract fun run(block: QueryRunner.() -> Unit)

    abstract fun run(query: String, params: Map<String, Any?>): Result

    fun run(cypher: Cypher) =
        run(cypher.query, cypher.params)

    fun runGraphQL(query: GraphQLQuery, params: Params) =
        run(GraphQLProvider.translate(query, params))
}