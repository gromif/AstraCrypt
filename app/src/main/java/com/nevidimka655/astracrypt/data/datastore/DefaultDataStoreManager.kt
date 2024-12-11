package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class DefaultDataStoreManager(
    private val dataStore: DataStore<Preferences>
) {

    private val PASSWORD_CHECK_TEST_DATA = stringPreferencesKey("papaya")
    suspend fun getPasswordCheckTestData() = dataStore.data.first()[PASSWORD_CHECK_TEST_DATA] ?: ""

    suspend fun setPasswordCheckTestDataFlow(value: String) = dataStore.edit { preferences ->
        preferences[PASSWORD_CHECK_TEST_DATA] = value
    }

}