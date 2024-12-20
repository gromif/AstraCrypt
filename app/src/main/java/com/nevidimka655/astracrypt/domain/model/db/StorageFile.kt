package com.nevidimka655.astracrypt.domain.model.db

import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StorageFile(
    @SerialName("a") val preview: String,
    @SerialName("b") val aead: KeysetTemplates.Stream
)