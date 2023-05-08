package pl.edu.agh.db2.dronkashop.framework.core

data class ID(val value: Int = INVALID_VALUE) {
    fun isValid() =
        value != INVALID_VALUE

    companion object {
        const val INVALID_VALUE: Int = Int.MIN_VALUE
        val INVALID = ID(INVALID_VALUE)
    }
}