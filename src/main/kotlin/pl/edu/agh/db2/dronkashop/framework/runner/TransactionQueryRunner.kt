package pl.edu.agh.db2.dronkashop.framework.runner

import org.neo4j.driver.Result
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider

class TransactionQueryRunner : QueryRunner() {
    private val session = DBProvider.session()
    private val transaction = session.beginTransaction()

    fun commit() =
        transaction.commit()

    fun rollback() =
        transaction.rollback()

    override fun run(block: QueryRunner.() -> Unit) {
        try {
            block(this)
        } catch (e: Throwable) {
            System.err.println("TransactionQueryRunner: unhandled exception")
            System.err.println("Transaction will be rolled back")
            e.printStackTrace()
        }
    }

    override fun run(query: String, params: Map<String, Any?>): Result =
        transaction.run(query, params)

    override fun close() {
        transaction.close()
        session.close()
    }
}