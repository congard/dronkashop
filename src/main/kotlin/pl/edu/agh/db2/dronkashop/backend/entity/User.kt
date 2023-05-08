package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation
import java.time.LocalDateTime

class User : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/user/UserUpdateProperties.graphql")

    // TODO
    override val mutatePropertiesQuery: GraphQLQuery = ""

    var displayName: String = ""
    var login: String = ""
    var email: String = ""
    var registrationDate: LocalDateTime = LocalDateTime.MIN
    var sells = ToManyRelation<Item>()
}