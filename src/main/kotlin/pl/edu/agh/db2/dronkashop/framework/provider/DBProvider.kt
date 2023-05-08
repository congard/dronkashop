package pl.edu.agh.db2.dronkashop.framework.provider

import DBCredentials
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session

/**
 * Opens a database connection
 */
object DBProvider : AutoCloseable {
    val driver: Driver

    init {
        val authToken = AuthTokens.basic(DBCredentials.USER, DBCredentials.PASSWORD)
        driver = GraphDatabase.driver(DBCredentials.URI, authToken)
        println("DBProvider.init")
    }

    fun session(): Session =
        driver.session()

    override fun close() {
        driver.close()
        println("DBProvider.close")
    }
}