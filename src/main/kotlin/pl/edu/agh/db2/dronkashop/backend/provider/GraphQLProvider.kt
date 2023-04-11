package pl.edu.agh.db2.dronkashop.backend.provider

import org.neo4j.graphql.Cypher
import org.neo4j.graphql.QueryContext
import org.neo4j.graphql.SchemaBuilder
import org.neo4j.graphql.Translator
import pl.edu.agh.db2.dronkashop.backend.Resource

/**
 * Translates GraphQL queries to Cypher
 */
object GraphQLProvider {
    private val context = QueryContext()
    private val translator: Translator

    init {
        val sdl = Resource.gets("/schema.graphql")
        val schema = SchemaBuilder.buildSchema(sdl)
        translator = Translator(schema)
    }

    fun translate(query: String, params: Map<String, Any>): Cypher =
        translator.translate(query, params, context)[0]
}