package pl.edu.agh.db2.dronkashop.framework.core

typealias Params = HashMap<String, Any?>

fun paramsOf(vararg pairs: Pair<String, Any?>): Params =
    hashMapOf(*pairs)
