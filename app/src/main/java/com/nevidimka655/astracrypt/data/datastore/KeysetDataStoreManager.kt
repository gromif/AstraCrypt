package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class KeysetDataStoreManager(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun get(key: String): String? {
        val prefsKey = stringPreferencesKey(name = key)
        return dataStore.data.map { it[prefsKey] }.first()
    }

    suspend fun set(key: String, serializedKeyset: String) {
        val prefsKey = stringPreferencesKey(name = key)
        dataStore.edit { it[prefsKey] = serializedKeyset }
    }

}