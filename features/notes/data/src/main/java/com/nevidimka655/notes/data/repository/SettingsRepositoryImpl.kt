package com.nevidimka655.notes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nevidimka655.domain.notes.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private val aeadKey = intPreferencesKey("aead")
    override fun getAeadTemplateIndexFlow(): Flow<Int> {
        return dataStore.data.map { it[aeadKey] ?: -1 }
    }

    override suspend fun getAeadTemplateIndex(): Int {
        return getAeadTemplateIndexFlow().first()
    }

    override suspend fun setAeadTemplateIndex(aead: Int) {
        dataStore.edit {
            it[aeadKey] = aead
        }
    }
}