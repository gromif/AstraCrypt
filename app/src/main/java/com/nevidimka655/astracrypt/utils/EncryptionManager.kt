package com.nevidimka655.astracrypt.utils

import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import com.nevidimka655.crypto.tink.KeysetTemplates
import kotlinx.coroutines.flow.first

class EncryptionManager(
    private val settingsDataStoreManager: SettingsDataStoreManager
) {
    private var info: EncryptionInfo? = null

    suspend fun getInfo() = info ?: settingsDataStoreManager.encryptionInfoFlow.first()
        .also { info = it }

    fun getCachedInfo() = info

    suspend fun save() {
        settingsDataStoreManager.setEncryptionInfo(this@EncryptionManager.db())
    }

    suspend fun getFileEncryptionName() = KeysetTemplates.Stream.entries
        .getOrNull(this@EncryptionManager.db().fileEncryptionOrdinal)?.name

    suspend fun getThumbEncryptionName() = KeysetTemplates.Stream.entries
        .getOrNull(this@EncryptionManager.db().thumbEncryptionOrdinal)?.name

    suspend fun getDbEncryptionName() = KeysetTemplates.AEAD.entries
        .getOrNull(this@EncryptionManager.db().databaseEncryptionOrdinal)?.name

    suspend fun getNotesEncryptionName() = KeysetTemplates.AEAD.entries
        .getOrNull(this@EncryptionManager.db().notesEncryptionOrdinal)?.name

}