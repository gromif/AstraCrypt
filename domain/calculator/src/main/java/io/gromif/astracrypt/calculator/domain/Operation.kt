package io.gromif.astracrypt.calculator.domain

sealed class Operation(val symbol: String) {
    data object Add: Operation("+")
    data object Subtract: Operation("-")
    data object Multiply: Operation("×")
    data object Divide: Operation("/")
}