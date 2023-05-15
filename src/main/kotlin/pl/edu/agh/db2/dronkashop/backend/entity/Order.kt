package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation
import java.time.LocalDateTime
import java.util.*

class Order : Entity()  {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/order/OrderUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/order/OrderMutateProperties.graphql")

    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/order/OrderMutateRelations.graphql")

    var isPayed: Boolean = false
    var isCancelled: Boolean = false
    var date: LocalDateTime = LocalDateTime.MIN
    var items = ToManyRelation<OrderedItem>() // TODO: for this relations add extra function and, possible, override push
    var by = ToOneRelation.create<User>()
    var payedWith = ToOneRelation.create<Payment>()

    fun addItems(items: List<Item>) {
        TODO()
    }
}