package com.nevidimka655.astracrypt.auth.domain

import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAuthFlow(): Flow<Auth>

    suspend fun setHintVisibility(auth: Auth, visible: Boolean)

    suspend fun setHintText(auth: Auth, text: String)

    suspend fun setPassword(auth: Auth, password: String?)

    suspend fun verifyPassword(password: String)

    suspend fun disable(auth: Auth)

    suspend fun verifyCalculatorCombination(
        calculator: Skin.Calculator,
        combination: String
    ): Boolean

    suspend fun setSkinCalculator(auth: Auth, combination: String)

    suspend fun setSkinDefault(auth: Auth)

}