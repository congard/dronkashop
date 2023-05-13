package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity

class OrderedItem : Entity() {

    // TODO
    override val updatePropertiesQuery: GraphQLQuery = ""

    // TODO
    override val mutatePropertiesQuery: GraphQLQuery = ""

    var order: Order = Order();
    var item: Item = Item();
    var quantity: Int = -1;
}