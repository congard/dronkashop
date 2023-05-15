package pl.edu.agh.db2.dronkashop.framework.provider

import DBCredentials
import org.neo4j.driver.*
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import pl.edu.agh.db2.dronkashop.framework.runner.SessionQueryRunner

/**
 * Opens a database connection
 */
object DBProvider : AutoCloseable {
    val driver: Driver
    val defaultQueryRunner: QueryRunner

    init {
        val authToken = AuthTokens.basic(DBCredentials.USER, DBCredentials.PASSWORD)
        driver = GraphDatabase.driver(DBCredentials.URI, authToken)
        defaultQueryRunner = SessionQueryRunner()
        println("DBProvider.init")
    }

    fun session(): Session =
        driver.session()

    fun transaction(block: Transaction.() -> Unit) =
        session().use { session -> session.beginTransaction().use { block(it) } }

    override fun close() {
        defaultQueryRunner.close()
        driver.close()
        println("DBProvider.close")
    }
}