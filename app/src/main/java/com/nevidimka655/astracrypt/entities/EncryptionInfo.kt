package com.nevidimka655.astracrypt.entities

import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetGroupId
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EncryptionInfo(
    @SerialName("a") val fileEncryptionOrdinal: Int = 0,
    @SerialName("b") val thumbEncryptionOrdinal: Int = -1,
    @SerialName("c") val databaseEncryptionOrdinal: Int = -1,
    @SerialName("c1") val notesEncryptionOrdinal: Int = 3,

    @SerialName("e") val isNameEncrypted: Boolean = false,
    @SerialName("f") val isPathEncrypted: Boolean = false,
    @SerialName("g") val isFlagsEncrypted: Boolean = false,
    @SerialName("h") val isThumbnailEncrypted: Boolean = false,
    @SerialName("i") val isEncryptionTypeEncrypted: Boolean = false,
    @SerialName("j") val isThumbEncryptionTypeEncrypted: Boolean = false,

    @SerialName("k") val isAssociatedDataEncrypted: Boolean = false
) {
    private val dbKeysetHandle by lazy {
        KeysetFactory.aead(
            Engine.appContext,
            KeysetTemplates.AEAD.entries[databaseEncryptionOrdinal]
        )
    }
    val dbPrimitive by lazy { dbKeysetHandle.aeadPrimitive() }
    private val notesKeysetHandle by lazy {
        KeysetFactory.aead(
            Engine.appContext,
            KeysetTemplates.AEAD.entries[notesEncryptionOrdinal],
            keysetGroupId = KeysetGroupId.AEAD_NOTES
        )
    }
    val notesPrimitive by lazy { notesKeysetHandle.aeadPrimitive() }
    val dbKeyId by lazy { dbKeysetHandle.primary.id }
    val isDatabaseEncrypted by lazy { databaseEncryptionOrdinal != -1 }
    val isNotesEncrypted by lazy { notesEncryptionOrdinal != -1 }
}