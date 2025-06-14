package io.gromif.astracrypt.utils.crypto

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.gromif.crypto.tink.keyset.io.KeysetReader
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DatastoreKeysetReader(
    private val dataStore: DataStore<Preferences>
) : KeysetReader {

    override suspend fun read(key: String): String? {
        val prefsKey = stringPreferencesKey(name = key)
        return dataStore.data.map { it[prefsKey] }.first()
    }
}
