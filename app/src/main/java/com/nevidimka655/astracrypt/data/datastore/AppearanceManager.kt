package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class AppearanceManager(
    private val dataStore: DataStore<Preferences>
) {

    private val dynamicTheme = booleanPreferencesKey("lime")
    val dynamicThemeFlow = dataStore.data.map { it[dynamicTheme] != false }
    suspend fun setDynamicTheme(enabled: Boolean) = dataStore.edit { preferences ->
        preferences[dynamicTheme] = enabled
    }

}