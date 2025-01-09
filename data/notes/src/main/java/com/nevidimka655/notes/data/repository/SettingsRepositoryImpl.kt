package com.nevidimka655.notes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nevidimka655.domain.notes.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private val aeadKey = intPreferencesKey("aead")
    override suspend fun getAeadTemplateIndex(): Int {
        return dataStore.data.first()[aeadKey] ?: -1
    }

    override suspend fun setAeadTemplateIndex(aead: Int) {
        dataStore.edit {
            it[aeadKey] = aead
        }
    }
}