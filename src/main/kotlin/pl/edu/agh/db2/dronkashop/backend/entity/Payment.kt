package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import java.time.LocalDateTime

class Payment : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/payment/PaymentUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/payment/PaymentMutateProperties.graphql")

    // TODO: test
    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/payment/PaymentMutateRelations.graphql")

    override val createNodeQuery: GraphQLQuery =
        Resource.gets("/mutation/payment/PaymentCreate.graphql")

    var date: LocalDateTime = LocalDateTime.MIN
    var amount: Float = -1.0f
    var type: String = ""
    var belongsTo = ToOneRelation.create<Order>()

    fun setOrder(order: Order, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        order.setPayment(this, runner)

    fun removeFromOrder(order: Order) =
        order.removePayment(this)

}