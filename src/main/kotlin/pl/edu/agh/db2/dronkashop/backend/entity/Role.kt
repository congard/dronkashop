package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation

// Can not make Role an enum:
// "Enum class cannot inherit from classes"
// > Ok, I meant something else, but still ok
class Role : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/role/RoleUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/role/RoleMutateProperties.graphql")

    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/role/RoleMutateRelations.graphql")

    override val createNodeQuery: GraphQLQuery =
        Resource.gets("/mutation/role/RoleCreate.graphql")

    var name: String = ""
    var description: String = ""
    var includes = ToManyRelation<User>()

    fun addUser(user: User) =
        user.setRole(this)
}