package pl.edu.agh.db2.dronkashop.backend

import pl.edu.agh.db2.dronkashop.backend.entity.Item
import pl.edu.agh.db2.dronkashop.framework.entity.Relation
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.backend.provider.ItemProvider

fun main() {
    DBProvider.use { start() }
}

fun start() {
    val item22: Item = ItemProvider.getById(ID(22)).also { println(it) }
    item22.belongsTo.forEach { categoryRelation -> println(categoryRelation.dst()) }

    val pizza: List<Item> = ItemProvider.getByName("pizza").also { println(it) }
    println(pizza.first().publishedBy.dst())

    ItemProvider.getByName("Chleb")[0].let { bread ->
        fun printModified() =
            println("bread.isModified: ${bread.isModified()}")

        printModified()

        bread.description = "Qwerty"
        bread.price = 12.45f
        bread.publishedBy = Relation.create(to = ID(24))
        bread.belongsTo.add(Relation.create(to = ID(175)))

        printModified()
        bread.push()
        printModified()

        println(bread)
    }
}