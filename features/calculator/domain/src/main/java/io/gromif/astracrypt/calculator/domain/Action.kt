package io.gromif.astracrypt.calculator.domain

sealed class Action {
    data class Number(val number: Int): Action()
    data object Clear: Action()
    data object Delete: Action()
    data class Operation(val operation: io.gromif.astracrypt.calculator.domain.Operation): Action()
    data object Calculate: Action()
    data object Decimal: Action()
}