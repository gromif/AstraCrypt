package io.gromif.astracrypt.data.crypto

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.gromif.crypto.tink.model.KeysetWriter

class DatastoreKeysetWriter(
    private val dataStore: DataStore<Preferences>
) : KeysetWriter {

    override suspend fun write(key: String, keyset: String) {
        val prefsKey = stringPreferencesKey(name = key)
        dataStore.edit { it[prefsKey] = keyset }
    }

}