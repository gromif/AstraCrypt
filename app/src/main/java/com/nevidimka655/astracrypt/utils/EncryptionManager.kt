package com.nevidimka655.astracrypt.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsKeys
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptionManager {
    var encryptionInfo by mutableStateOf(EncryptionInfo())

    fun loadEncryptionInfo() {
        val serializedInfo = PrefsManager.settings.getString(
            key = PrefsKeys.ENCRYPTION,
            defaultValue = ""
        )
        encryptionInfo = if (!serializedInfo.isNullOrEmpty()) {
            val info = Json.decodeFromString<EncryptionInfo>(serializedInfo)
            if (info.fileEncryptionOrdinal > 15 || info.fileEncryptionOrdinal < -1) info.copy(
                fileEncryptionOrdinal = decodeIntField(
                    field = info.fileEncryptionOrdinal,
                    mod = 38
                ),
                thumbEncryptionOrdinal = decodeIntField(
                    field = info.thumbEncryptionOrdinal,
                    mod = 84
                ),
                databaseEncryptionOrdinal = decodeIntField(
                    field = info.databaseEncryptionOrdinal,
                    mod = 62
                )
            ) else info
        } else EncryptionInfo()
    }

    fun save() {
        PrefsManager.settings.putString(
            key = PrefsKeys.ENCRYPTION,
            value = Json.encodeToString(encryptionInfo)
        ).commit()
    }

    fun decodeIntField(field: Int, mod: Int) = ((KeysetFactory.uniqueSalt + field) / mod) - 12

    fun getFileEncryptionName() = if (encryptionInfo.fileEncryptionOrdinal > -1) {
        KeysetTemplates.Stream.entries[encryptionInfo.fileEncryptionOrdinal].name
    } else null

    fun getThumbEncryptionName() = if (encryptionInfo.thumbEncryptionOrdinal > -1) {
        KeysetTemplates.Stream.entries[encryptionInfo.thumbEncryptionOrdinal].name
    } else null

    fun getDbEncryptionName() = if (encryptionInfo.databaseEncryptionOrdinal > -1) {
        KeysetTemplates.AEAD.entries[encryptionInfo.databaseEncryptionOrdinal].name
    } else null

    fun getNotesEncryptionName() = if (encryptionInfo.notesEncryptionOrdinal > -1) {
        KeysetTemplates.AEAD.entries[encryptionInfo.notesEncryptionOrdinal].name
    } else null

}