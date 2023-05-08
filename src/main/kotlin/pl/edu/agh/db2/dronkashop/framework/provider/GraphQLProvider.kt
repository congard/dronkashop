package pl.edu.agh.db2.dronkashop.framework.provider

import org.neo4j.graphql.Cypher
import org.neo4j.graphql.QueryContext
import org.neo4j.graphql.SchemaBuilder
import org.neo4j.graphql.Translator
import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.Params
import pl.edu.agh.db2.dronkashop.framework.core.SchemaPreprocessor

/**
 * Translates GraphQL queries to Cypher
 */
object GraphQLProvider {
    private val context = QueryContext()
    private val translator: Translator

    init {
        val sdl = SchemaPreprocessor.process(Resource.gets("/schema/schema.graphql"))
        val schema = SchemaBuilder.buildSchema(sdl)
        translator = Translator(schema)
    }

    fun translate(query: GraphQLQuery, params: Params): Cypher =
        translator.translate(query, params, context)[0]
}