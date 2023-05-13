package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation

// Can not make Role an enum:
// "Enum class cannot inherit from classes"
class Role : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/role/RoleUpdateProperties.graphql")

    // TODO
    override val mutatePropertiesQuery: GraphQLQuery = ""

    var name: String = ""
    var description: String = ""
    var includes = ToManyRelation<User>()
}