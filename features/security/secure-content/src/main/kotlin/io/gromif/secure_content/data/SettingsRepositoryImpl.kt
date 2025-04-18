package io.gromif.secure_content.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private val modeKey = intPreferencesKey("secureContent_mode")

    override fun getSecureContentModeFlow(): Flow<SecureContentMode> {
        return dataStore.data.map {
            val value = it[modeKey]
            value?.let { SecureContentMode.entries[it] } ?: SecureContentMode.ENABLED
        }
    }

    override suspend fun setSecureContentMode(mode: SecureContentMode) {
        dataStore.edit { it[modeKey] = mode.ordinal }
    }
}