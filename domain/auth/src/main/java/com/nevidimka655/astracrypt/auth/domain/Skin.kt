package com.nevidimka655.astracrypt.auth.domain

sealed class Skin {

    data class Calculator(
        val combinationHash: String
    ): Skin()

}