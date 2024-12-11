package com.nevidimka655.astracrypt.data.model

import com.nevidimka655.astracrypt.data.room.DatabaseColumns
import com.nevidimka655.crypto.tink.KeysetTemplates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseAeadInfo(
    @SerialName("a") val aead: KeysetTemplates.AEAD,
    @SerialName("b") val columns: List<DatabaseColumns>
)