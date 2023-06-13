package pl.edu.agh.db2.dronkashop.backend

import pl.edu.agh.db2.dronkashop.backend.entity.Item
import pl.edu.agh.db2.dronkashop.backend.entity.Role
import pl.edu.agh.db2.dronkashop.backend.provider.RoleProvider
import pl.edu.agh.db2.dronkashop.backend.provider.UserProvider
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import java.time.LocalDateTime

fun main() {
    DBProvider.use { start() }
}

fun queryNodes() {
    val user176 = UserProvider.getById(ID(176))
    println(user176)

    // tworzymy nowe scope, żeby nie zaśmiecać globalne
    run {
        val roleId = user176.belongsTo.dstId() // pobieramy tylko same id
        val role = EntityProvider.entityById<Role>(roleId, incomplete = true)

        assert(role.isIncomplete())
        println("Encja jest niepełna: ${role.isIncomplete()}")

        user176.belongsTo.dst() // odczytujemy relacje

        assert(!role.isIncomplete())
        println("Encja jest niepełna: ${role.isIncomplete()}")
    }

    val (roleClient) = RoleProvider.getByName("Client")
    println(roleClient)
}

fun createItem() {
    Item().apply {
        name = "Kawa z mlekiem"
        description = "Smaczna kawa z dodatkiem mleka"
        quantity = 10
        price = 7.5f
        date = LocalDateTime.now()
        isDiscontinued = false

        // encja nie ma przypisanego id
        assert(id == ID.INVALID)

        // utrwalamy encję
        persist() // albo push()

        // po utrwaleniu encja już ma przypisane id
        assert(id != ID.INVALID)

        println(this)
    }
}

fun start() {
    queryNodes()
    // createItem()
}