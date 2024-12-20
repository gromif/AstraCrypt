package com.nevidimka655.astracrypt.data.model

import com.nevidimka655.astracrypt.data.database.DatabaseColumns
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private typealias AeadStream = KeysetTemplates.Stream
private typealias Aead = KeysetTemplates.AEAD

@Serializable
data class AeadInfo(
    @SerialName("a") val file: AeadStream? = AeadStream.AES128_GCM_HKDF_1MB,
    @SerialName("b") val preview: AeadStream? = null,
    @SerialName("c") val database: DatabaseAeadInfo? = null,
    @SerialName("d") val aeadNotes: Aead? = Aead.AES128_GCM,

    @SerialName("e") val bindAssociatedData: Boolean = false
) {
    val db get() = database != null
    val notes by lazy { aeadNotes != null }

    val name get() = database?.columns?.contains(DatabaseColumns.Name) == true
    val path get() = database?.columns?.contains(DatabaseColumns.File) == true
    val flags get() = database?.columns?.contains(DatabaseColumns.Flags) == true
    val thumb get() = database?.columns?.contains(DatabaseColumns.Preview) == true
}