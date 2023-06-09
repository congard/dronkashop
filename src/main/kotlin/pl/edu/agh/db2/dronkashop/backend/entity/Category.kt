package pl.edu.agh.db2.dronkashop.backend.entity

import pl.edu.agh.db2.dronkashop.backend.Resource
import pl.edu.agh.db2.dronkashop.framework.core.GraphQLQuery
import pl.edu.agh.db2.dronkashop.framework.core.paramsOf
import pl.edu.agh.db2.dronkashop.framework.entity.Entity
import pl.edu.agh.db2.dronkashop.framework.entity.ToManyRelation
import pl.edu.agh.db2.dronkashop.framework.entity.annotations.Ignore
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner

class Category : Entity() {
    override val updatePropertiesQuery: GraphQLQuery =
        Resource.gets("/query/category/CategoryUpdateProperties.graphql")

    override val mutatePropertiesQuery: GraphQLQuery =
        Resource.gets("/mutation/category/CategoryMutateProperties.graphql")

    override val mutateRelationsQuery: GraphQLQuery =
        Resource.gets("/mutation/category/CategoryMutateRelations.graphql")

    override val createNodeQuery: GraphQLQuery =
        Resource.gets("/mutation/category/CategoryCreate.graphql")

    @Ignore
    private val mutateAddItem: GraphQLQuery =
        Resource.gets("/mutation/category/CategoryMutateAddItemToCategory.graphql")

    @Ignore
    private val mutateRemoveItem: GraphQLQuery =
        Resource.gets("/mutation/category/CategoryMutateRemoveItemFromCategory.graphql")

    var name: String = ""
    var description: String = ""
    var includes = ToManyRelation<Item>()

    fun addItem(item: Item, runner: QueryRunner = DBProvider.defaultQueryRunner) {
        runCustomMutation(mutateAddItem, paramsOf("itemId" to item.id.value), runner)
        item.pull()
    }

    fun removeItem(item: Item, runner: QueryRunner = DBProvider.defaultQueryRunner) {
        runCustomMutation(mutateRemoveItem, paramsOf("itemId" to item.id.value), runner)
        item.pull()
    }
}