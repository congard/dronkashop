package pl.edu.agh.db2.dronkashop.backend.provider

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session

/**
 * Opens a database connection
 */
object DBProvider : AutoCloseable {
    private const val URI = "neo4j+s://935b3042.databases.neo4j.io"
    private const val USER = "neo4j"
    private const val PASSWORD = "jp5FIR7474gCHcWUjBgoQbC4rdCcu2fjUR8bpkFPx48"

    val driver: Driver

    init {
        val authToken = AuthTokens.basic(USER, PASSWORD)
        driver = GraphDatabase.driver(URI, authToken)
        println("DBProvider.init")
    }

    fun session(): Session =
        driver.session()

    override fun close() {
        driver.close()
        println("DBProvider.close")
    }
}