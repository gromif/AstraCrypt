package com.nevidimka655.astracrypt.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import kotlinx.coroutines.flow.first

class EncryptionManager(
    private val settingsDataStoreManager: SettingsDataStoreManager,
    private val keysetFactory: KeysetFactory
) {
    private var info: EncryptionInfo? = null
    var encryptionInfoState by mutableStateOf(EncryptionInfo())

    suspend fun getInfo() = info ?: settingsDataStoreManager.encryptionInfoFlow.first()
        .also { info = it }

    suspend fun loadEncryptionInfo() {
        encryptionInfoState = settingsDataStoreManager.encryptionInfoFlow.first().let { info ->
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
        }
    }

    suspend fun save() {
        settingsDataStoreManager.setEncryptionInfo(encryptionInfoState)
    }

    fun decodeIntField(field: Int, mod: Int) = ((keysetFactory.uniqueSalt + field) / mod) - 12

    fun getFileEncryptionName() = if (encryptionInfoState.fileEncryptionOrdinal > -1) {
        KeysetTemplates.Stream.entries[encryptionInfoState.fileEncryptionOrdinal].name
    } else null

    fun getThumbEncryptionName() = if (encryptionInfoState.thumbEncryptionOrdinal > -1) {
        KeysetTemplates.Stream.entries[encryptionInfoState.thumbEncryptionOrdinal].name
    } else null

    fun getDbEncryptionName() = if (encryptionInfoState.databaseEncryptionOrdinal > -1) {
        KeysetTemplates.AEAD.entries[encryptionInfoState.databaseEncryptionOrdinal].name
    } else null

    fun getNotesEncryptionName() = if (encryptionInfoState.notesEncryptionOrdinal > -1) {
        KeysetTemplates.AEAD.entries[encryptionInfoState.notesEncryptionOrdinal].name
    } else null

}