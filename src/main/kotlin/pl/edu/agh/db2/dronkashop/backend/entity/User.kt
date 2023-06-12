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

    // TODO: private setter for registrationDate

    var displayName: String = ""
    var login: String = ""
    var email: String = ""
    var registrationDate: LocalDateTime = LocalDateTime.MIN
    var sells = ToManyRelation<Item>()

    // TODO: add mutations
    var has = ToManyRelation<Order>()
    var belongsTo = ToOneRelation.create<Role>()

    fun addItem(item: Item) {
        runCustomMutation(mutateAddItem, paramsOf("itemId" to item.id.value))
        item.pull()
    }

    fun addOrder(order: Order) {
        runCustomMutation(mutateAddOrder, paramsOf("orderId" to order.id.value))
        order.pull()
    }

    fun setRole(role: Role) {
        runCustomMutation(mutateAddRole, paramsOf("roleId" to role.id.value))
        role.pull()
    }
}