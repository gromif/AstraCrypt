package com.nevidimka655.astracrypt.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    @SerialName("a") val type: Int = -1,
    @SerialName("b") val skin: SkinDto? = null,
    @SerialName("c") val hintState: Boolean = false,
    @SerialName("d") val hintText: String? = null,
    @SerialName("e") val bindTinkAd: Boolean = false
)