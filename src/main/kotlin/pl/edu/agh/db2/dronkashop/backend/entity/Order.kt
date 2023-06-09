package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.paramsOf
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation
import pl.edu.agh.db2.dronkashop.framework.entity.annotations.Ignore
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import java.time.LocalDateTime

class Order : Entity()  {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/order/OrderUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/order/OrderMutateProperties.graphql")

    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/order/OrderMutateRelations.graphql")

    override val createNodeQuery: GraphQLQuery =
        Resource.gets("/mutation/order/OrderCreate.graphql")

    @Ignore
    private val mutateAddPayment: GraphQLQuery =
        Resource.gets("/mutation/order/OrderMutateAddPaymentToOrder.graphql")

    @Ignore
    private val mutateRemovePayment: GraphQLQuery =
        Resource.gets("/mutation/order/OrderMutateRemovePaymentFromOrder.graphql")

    var isPayed: Boolean = false
    var isCancelled: Boolean = false
    var date: LocalDateTime = LocalDateTime.MIN
    var items = ToManyRelation<OrderedItem>() // TODO: for this relations add extra function and, possible, override push
    var by = ToOneRelation.create<User>()
    var payedWith = ToOneRelation.create<Payment>()

    fun addItems(items: List<Item>) {
        TODO()
    }

    fun setPayment(payment: Payment, runner: QueryRunner = DBProvider.defaultQueryRunner) {
        runCustomMutation(mutateAddPayment, paramsOf("paymentId" to payment.id.value), runner)
        payment.pull()
    }
    fun removePayment(payment: Payment, runner: QueryRunner = DBProvider.defaultQueryRunner) {
        runCustomMutation(mutateRemovePayment, paramsOf("paymentId" to payment.id.value), runner)
        payment.pull()
    }

    fun setCustomer(user: User, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        user.addOrder(this, runner)

    fun removeFromCustomer(user: User, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        user.removeOrder(this, runner)
}