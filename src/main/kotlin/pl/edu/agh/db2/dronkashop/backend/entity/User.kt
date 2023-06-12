package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.paramsOf
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation
import pl.edu.agh.db2.dronkashop.framework.entity.annotations.Ignore
import java.time.LocalDateTime

class User : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/user/UserUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateProperties.graphql")

    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateRelations.graphql")

    override val createNodeQuery: GraphQLQuery =
        Resource.gets("/mutation/user/UserCreate.graphql")

    @Ignore
    private val mutateAddItem: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateAddItemToUser.graphql")

    @Ignore
    private val mutateAddOrder: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateAddOrderToUser.graphql")

    @Ignore
    private val mutateAddRole: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateAddRoleToUser.graphql")

    @Ignore
    private val mutateRemoveItem: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateRemoveItemFromUser.graphql")

    @Ignore
    private val mutateRemoveOrder: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateRemoveOrderFromUser.graphql")

    @Ignore
    private val mutateRemoveRole: GraphQLQuery =
        Resource.gets("/mutation/user/UserMutateRemoveRoleFromUser.graphql")

    // TODO: private setter for registrationDate

    var displayName: String = ""
    var login: String = ""
    var email: String = ""
    var registrationDate: LocalDateTime = LocalDateTime.MIN
    var sells = ToManyRelation<Item>()
    var has = ToManyRelation<Order>()
    var belongsTo = ToOneRelation.create<Role>()

    fun addItem(item: Item) {
        runCustomMutation(mutateAddItem, paramsOf("itemId" to item.id.value))
        item.pull()
    }

    fun removeItem(item: Item) {
        runCustomMutation(mutateRemoveItem, paramsOf("itemId" to item.id.value))
        item.pull()
    }

    fun addOrder(order: Order) {
        runCustomMutation(mutateAddOrder, paramsOf("orderId" to order.id.value))
        order.pull()
    }

    fun removeOrder(order: Order) {
        runCustomMutation(mutateRemoveOrder, paramsOf("orderId" to order.id.value))
        order.pull()
    }

    fun setRole(role: Role) {
        runCustomMutation(mutateAddRole, paramsOf("roleId" to role.id.value))
        role.pull()
    }

    fun removeRole(role: Role) {
        runCustomMutation(mutateRemoveRole, paramsOf("roleId" to role.id.value))
        role.pull()
    }
}