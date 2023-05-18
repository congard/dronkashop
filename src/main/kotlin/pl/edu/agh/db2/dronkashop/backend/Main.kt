package pl.edu.agh.db2.dronkashop.backend

import pl.edu.agh.db2.dronkashop.backend.entity.*
import pl.edu.agh.db2.dronkashop.backend.provider.*
import pl.edu.agh.db2.dronkashop.framework.entity.Relation
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider

fun main() {
    DBProvider.use { start() }
}

fun start() {

    // testing Order, Payment, Role, CategoryProvider, UserProvider
    val order150: Order = OrderProvider.getById(ID(150)).also { println(it) }
    println(order150.by.dst())

    order150.items.forEach { relation ->
        relation.dst().let {
            println(it)
            println(it.order.dst())
            println(it.item.dst())
            it.quantity = 100
            it.push()
            println(it)
        }
    }

    val payment92: Payment = PaymentProvider.getById(ID(92)).also { println(it) }
    println(payment92.amount)

    val role25: Role = RoleProvider.getById(ID(25)).also { println(it) }
    role25.includes.forEach { userRelation -> println(userRelation.dst()) }

    val roleListClient: List<Role> = RoleProvider.getByName("Client").also { println(it) }
    println(roleListClient.first().description)

    val category23: Category = CategoryProvider.getById(ID(23)).also { println(it) }
    println(category23.name)
//    category23.also {
//        it.description = "Przekąski Test"
//        it.includes.add(Relation.create(ID(174)))
//        it.push()
//    }
    val categoryListNapoje: List<Category> = CategoryProvider.getByName("Napoje").also { println(it) }
    println(categoryListNapoje.first().description)

    val user176: User = UserProvider.getById(ID(176)).also { println(it) }
    println(user176.displayName)

    val userListBiedronka: List<User> = UserProvider.getByName("Biedronka").also { println(it) }
    println(userListBiedronka.first().email)

    println()

    // test persist
//    Category().run {
//        name = "Test Category"
//        description = "Test Description"
//        println(id)
//        persist()
//        println(id)
//    }

    // end of testing

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