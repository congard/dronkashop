package pl.edu.agh.db2.dronkashop.framework.runner

import org.neo4j.driver.Result
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import java.util.*

class SessionQueryRunner : QueryRunner() {
    private val session = DBProvider.session()

    override fun <R : Any> run(block: QueryRunner.() -> R): Optional<R> {
        try {
            return Optional.of(block(this))
        } catch (e: Throwable) {
            System.err.println("SessionQueryRunner: unhandled exception")
            e.printStackTrace()
        }

        return Optional.empty()
    }

    override fun run(query: String, params: Map<String, Any?>): Result =
        session.run(query, params)

    override fun close() {
        session.close()
    }
}