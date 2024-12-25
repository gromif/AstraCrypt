package com.nevidimka655.tink_lab.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyDto(

    @SerialName("a")
    val dataTypeId: Int,

    @SerialName("b")
    val aeadType: String,

    @SerialName("c")
    val encryptedKeyset: String,

    @SerialName("d")
    val hash: String

)