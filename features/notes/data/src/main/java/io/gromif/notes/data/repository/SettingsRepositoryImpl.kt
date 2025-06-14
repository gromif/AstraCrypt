package io.gromif.notes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private val aeadKey = intPreferencesKey("aead")
    override fun getAeadModeFlow(): Flow<AeadMode> {
        return dataStore.data.map {
            val aeadIndex = it[aeadKey] ?: KeysetTemplates.AEAD.XAES_256_GCM_192_BIT_NONCE_NO_PREFIX.ordinal
            when {
                aeadIndex == -1 -> AeadMode.None
                else -> AeadMode.Template(
                    id = aeadIndex,
                    name = KeysetTemplates.AEAD.entries[aeadIndex].name
                )
            }
        }
    }

    override suspend fun getAeadMode(): AeadMode {
        return getAeadModeFlow().first()
    }

    override suspend fun setAeadMode(aeadMode: AeadMode) {
        val aeadIndex = when(aeadMode) {
            AeadMode.None -> -1
            is AeadMode.Template -> aeadMode.id
        }
        dataStore.edit {
            it[aeadKey] = aeadIndex
        }
    }
}