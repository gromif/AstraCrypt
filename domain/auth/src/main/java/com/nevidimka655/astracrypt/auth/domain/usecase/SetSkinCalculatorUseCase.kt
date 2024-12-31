package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository

class SetSkinCalculatorUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(auth: Auth, combination: String) {
        repository.setSkinCalculator(
            auth = auth,
            combination = combination
        )
    }

}