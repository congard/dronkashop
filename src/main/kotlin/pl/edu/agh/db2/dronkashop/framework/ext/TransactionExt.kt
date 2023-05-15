package pl.edu.agh.db2.dronkashop.framework.ext

import org.neo4j.driver.Result
import org.neo4j.driver.Transaction
import org.neo4j.graphql.Cypher
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider

fun Transaction.run(cypher: Cypher): Result =
    this.run(cypher.query, cypher.params)

fun Transaction.runGraphQL(query: GraphQLQuery, params: Params) =
    run(GraphQLProvider.translate(query, params))
