package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation

class Category : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/category/CategoryUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/category/CategoryMutateProperties.graphql")

    // TODO
    override val mutateRelationsQuery: GraphQLQuery
        get() = super.mutateRelationsQuery

    var name: String = ""
    var description: String = ""
    var includes = ToManyRelation<Item>()
}