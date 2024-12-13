package com.nevidimka655.astracrypt.features.calculator

import com.nevidimka655.compose_calculator.CalculatorManager
import com.nevidimka655.astracrypt.view.security.authentication.Camouflage
import kotlinx.coroutines.channels.Channel

class CalculatorManagerWrap : CalculatorManager() {
    var camouflage: Camouflage.Calculator? = null
    val onCamouflagesClose = Channel<Boolean>()

    override suspend fun calculate() {
        val combination = state.number1.toIntOrNull()
        if (combination != null
            && combination == camouflage?.numberCombination?.take(MAX_NUM_LENGTH)?.toInt()
            && state.operation == null
        ) onCamouflagesClose.send(true)
        else super.calculate()
    }
}