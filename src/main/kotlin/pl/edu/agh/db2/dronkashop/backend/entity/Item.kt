package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation
import pl.edu.agh.db2.dronkashop.framework.entity.ToOneRelation
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner
import java.time.LocalDateTime

class Item : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/item/ItemUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/item/ItemMutateProperties.graphql")

    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/item/ItemMutateRelations.graphql")

    override val createNodeQuery: GraphQLQuery =
        Resource.gets("/mutation/item/ItemCreate.graphql")

    var name: String = ""
    var description: String = ""
    var quantity: Int = -1
    var price: Float = -1.0f
    var date: LocalDateTime = LocalDateTime.MIN
    var isDiscontinued: Boolean = true
    var belongsTo = ToManyRelation<Category>()
    var publishedBy = ToOneRelation.create<User>()

    fun addToCategory(category: Category, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        category.addItem(this, runner)

    fun removeFromCategory(category: Category, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        category.removeItem(this, runner)

    fun removeFromUser(user: User, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        user.removeItem(this, runner)

    fun setSupplier(user: User, runner: QueryRunner = DBProvider.defaultQueryRunner) =
        user.addItem(this, runner)
}