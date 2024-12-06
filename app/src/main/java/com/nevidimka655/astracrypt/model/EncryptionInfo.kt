package com.nevidimka655.astracrypt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EncryptionInfo(
    @SerialName("a") val fileEncryptionOrdinal: Int = 0,
    @SerialName("b") val thumbEncryptionOrdinal: Int = -1,
    @SerialName("c") val databaseEncryptionOrdinal: Int = -1,
    @SerialName("d") val notesEncryptionOrdinal: Int = 3,

    @SerialName("e") val name: Boolean = false,
    @SerialName("f") val path: Boolean = false,
    @SerialName("g") val flags: Boolean = false,
    @SerialName("h") val thumb: Boolean = false,
    @SerialName("i") val encryptionType: Boolean = false,
    @SerialName("j") val thumbEncryptionType: Boolean = false,

    @SerialName("k") val isAssociatedDataEncrypted: Boolean = false
) {
    val db by lazy { databaseEncryptionOrdinal != -1 }
    val notes by lazy { notesEncryptionOrdinal != -1 }
}