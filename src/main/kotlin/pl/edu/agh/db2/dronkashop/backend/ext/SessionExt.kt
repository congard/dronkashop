package pl.edu.agh.db2.dronkashop.backend.ext

import org.neo4j.driver.Result
import org.neo4j.driver.Session
import org.neo4j.graphql.Cypher

fun Session.run(cypher: Cypher): Result =
    this.run(cypher.query, cypher.params)
