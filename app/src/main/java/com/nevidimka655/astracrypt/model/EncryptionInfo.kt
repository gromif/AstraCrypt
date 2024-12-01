package com.nevidimka655.astracrypt.model

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
    val isDatabaseEncrypted by lazy { databaseEncryptionOrdinal != -1 }
    val isNotesEncrypted by lazy { notesEncryptionOrdinal != -1 }
}