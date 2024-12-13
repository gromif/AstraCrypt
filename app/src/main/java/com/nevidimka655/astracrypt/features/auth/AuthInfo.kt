package com.nevidimka655.astracrypt.features.auth

import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.security.authentication.Camouflage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfo(
    @SerialName("authType") val authType: AuthType = AuthType.NO_AUTH,
    @SerialName("camouflage") val camouflage: Camouflage = Camouflage.None,
    @SerialName("hintIsEnabled") val hintIsEnabled: Boolean = false,
    @SerialName("hint") val hint: String? = null
) {
    val authNameResId get() = when (authType) {
        AuthType.NO_AUTH -> R.string.withoutAuthentication
        AuthType.PASSWORD -> R.string.password
    }
    val camouflageNameResId get() = when (camouflage) {
        Camouflage.None -> R.string.settings_camouflageType_no
        is Camouflage.Calculator -> R.string.settings_camouflageType_calculator
    }
}