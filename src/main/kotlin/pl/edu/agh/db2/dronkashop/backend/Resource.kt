package pl.edu.agh.db2.dronkashop.backend

import java.net.URL

object Resource {
    operator fun get(name: String): URL =
        Resource::class.java.getResource(name)!!

    fun gets(name: String): String =
        get(name).readText()
}