package io.gromif.astracrypt.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    @SerialName("a") val type: Int,
    @SerialName("b") val skin: Int,
    @SerialName("c") val hintState: Boolean,
    @SerialName("d") val hintText: String?,
    @SerialName("e") val bindTinkAd: Boolean
)