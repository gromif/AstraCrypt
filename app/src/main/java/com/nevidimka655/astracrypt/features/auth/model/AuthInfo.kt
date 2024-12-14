package com.nevidimka655.astracrypt.features.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfo(
    @SerialName("a") val type: AuthType? = null,
    @SerialName("b") val skin: Skin? = null,
    @SerialName("c") val hintState: Boolean = false,
    @SerialName("d") val hintText: String? = null
)