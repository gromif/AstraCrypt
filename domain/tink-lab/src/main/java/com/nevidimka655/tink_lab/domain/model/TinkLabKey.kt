package com.nevidimka655.tink_lab.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TinkLabKey(

    @SerialName("a")
    val dataType: DataType = DataType.Files,

    @SerialName("b")
    val aeadType: String = "",

    @SerialName("c")
    val encryptedKeyset: String? = null,

    @SerialName("d")
    val hash: String = ""

)