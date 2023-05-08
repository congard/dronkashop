package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation

class Category : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/category/CategoryUpdateProperties.graphql")

    // TODO
    override val mutatePropertiesQuery: GraphQLQuery = ""

    var name: String = ""
    var description: String = ""
    var includes = ToManyRelation<Item>()
}