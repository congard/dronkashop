package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation
import java.time.LocalDateTime

class Payment : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/payment/PaymentUpdateProperties.graphql")

    // TODO
    override val mutatePropertiesQuery: GraphQLQuery = ""

    var date: LocalDateTime = LocalDateTime.MIN
    var amount: Float = -1.0f
    var type: String = ""
    var belongsTo = ToOneRelation.create<Order>()
}