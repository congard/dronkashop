package pl.edu.agh.db2.dronkashop.framework.core

import pl.edu.agh.db2.dronkashop.backend.Resource

/**
 * Available annotations:
 *  * `@embed('<path/to/file>')` – inserts the contents of the specified file
 */
object SchemaPreprocessor {
    fun process(schema: String): String {
        return schema.replace(Regex("@embed[(]'([a-zA-Z0-9\\-_/\\\\. ]+)'[)]")) { matchResult ->
            val groups = matchResult.groups
            Resource.gets(groups[1]!!.value)
        }
    }
}