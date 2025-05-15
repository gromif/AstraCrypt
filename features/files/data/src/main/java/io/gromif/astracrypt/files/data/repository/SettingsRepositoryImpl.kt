package io.gromif.astracrypt.files.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private val viewModeKey = intPreferencesKey("view_mode")
    override fun getViewModeFlow(): Flow<ViewMode> {
        return dataStore.data.map {
            val savedViewModeIndex = it[viewModeKey] ?: 0
            ViewMode.entries[savedViewModeIndex]
        }
    }

    override suspend fun setViewMode(viewMode: ViewMode) {
        dataStore.edit { preferences ->
            preferences[viewModeKey] = viewMode.ordinal
        }
    }

}