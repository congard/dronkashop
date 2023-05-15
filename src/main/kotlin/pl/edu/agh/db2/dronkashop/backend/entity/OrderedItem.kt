package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation

class OrderedItem : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/orderedItem/OrderedItemUpdateProperties.graphql")

    // TODO
    override val mutatePropertiesQuery: GraphQLQuery = ""

    var order = ToOneRelation.create<Order>()
    var item = ToOneRelation.create<Item>()
    var quantity: Int = -1
}