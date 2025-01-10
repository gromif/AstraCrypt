package io.gromif.astracrypt.settings.aead.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private val aeadKey = intPreferencesKey("aead")
    override fun getAeadNameFlow(): Flow<String?> {
        return dataStore.data.map {
            val ordinal = it[aeadKey] ?: -1
            KeysetTemplates.AEAD.entries.getOrNull(ordinal)?.name
        }
    }

    override suspend fun getAeadTemplateIndex(): Int {
        return dataStore.data.map { it[aeadKey] ?: -1 }.first()
    }

    override suspend fun setAeadTemplateIndex(aead: Int) {
        dataStore.edit { it[aeadKey] = aead }
    }
}