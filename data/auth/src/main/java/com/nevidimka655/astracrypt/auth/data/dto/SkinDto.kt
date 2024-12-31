package com.nevidimka655.astracrypt.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SkinDto {

    @Serializable
    data class Calculator(
        @SerialName("a")
        val combinationHash: String
    ): SkinDto()

}