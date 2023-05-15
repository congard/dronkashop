package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation

class OrderedItem : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/orderedItem/OrderedItemUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/orderedItem/OrderedItemMutateProperties.graphql")

    var order = ToOneRelation.create<Order>() // TODO: mark setter as private
    var item = ToOneRelation.create<Item>() // TODO: same as above
    var quantity: Int = -1
}