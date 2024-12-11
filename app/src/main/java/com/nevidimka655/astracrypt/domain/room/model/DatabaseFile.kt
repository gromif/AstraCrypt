package com.nevidimka655.astracrypt.domain.room.model

import com.nevidimka655.crypto.tink.KeysetTemplates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseFile(
    @SerialName("a") val preview: String,
    @SerialName("b") val aead: KeysetTemplates.Stream
)