package pl.edu.agh.db2.dronkashop.framework.ext

import org.neo4j.driver.Result
import org.neo4j.driver.Session
import org.neo4j.graphql.Cypher
import pl.edu.agh.db2.dronkashop.framework.provider.GraphQLProvider
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.Params

fun Session.run(cypher: Cypher): Result =
    this.run(cypher.query, cypher.params)

fun Session.runGraphQL(query: GraphQLQuery, params: Params) =
    run(GraphQLProvider.translate(query, params))
