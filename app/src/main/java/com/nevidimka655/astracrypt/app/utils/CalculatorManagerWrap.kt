package com.nevidimka655.astracrypt.app.utils

import com.nevidimka655.astracrypt.auth.domain.Skin
import com.nevidimka655.compose_calculator.CalculatorManager
import kotlinx.coroutines.channels.Channel

class CalculatorManagerWrap : CalculatorManager() {
    var skin: Skin.Calculator? = null
    val onCamouflagesClose = Channel<Boolean>()

    override suspend fun calculate() {
        val combination = state.number1.toIntOrNull()
        if (combination != null
            && combination == skin?.combinationHash?.take(MAX_NUM_LENGTH)?.toInt()
            && state.operation == null
        ) onCamouflagesClose.send(true)
        else super.calculate()
    }
}