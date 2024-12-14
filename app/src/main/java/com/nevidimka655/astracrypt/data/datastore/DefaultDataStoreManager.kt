package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.toBase64
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DefaultDataStoreManager(
    private val dataStore: DataStore<Preferences>
) {
    private val PASSWORD_HASH = stringPreferencesKey("papaya")
    val passwordHashFlow = dataStore.data.map {
        val hash = it[PASSWORD_HASH]
        if (!hash.isNullOrEmpty()) hash.fromBase64()
        else ByteArray(0)
    }
    suspend fun getPasswordHash() = passwordHashFlow.first()

    suspend fun setPasswordHash(hash: ByteArray?) = dataStore.edit { preferences ->
        preferences[PASSWORD_HASH] = hash?.toBase64() ?: ""
    }

}