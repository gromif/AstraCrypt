package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.Skin

class VerifyCalculatorCombinationUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(
        calculator: Skin.Calculator,
        combination: String
    ): Boolean {
        TODO()
    }

}