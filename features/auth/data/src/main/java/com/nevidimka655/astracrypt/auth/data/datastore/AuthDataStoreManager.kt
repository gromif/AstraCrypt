package com.nevidimka655.astracrypt.auth.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.encoders.Base64Util
import io.gromif.crypto.tink.extensions.fromBase64
import io.gromif.tink_datastore.TinkDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val base64Util: Base64Util,
    keysetManager: KeysetManager,
    tinkDataStoreParams: Params,
) : TinkDataStore(
    dataStore = dataStore,
    keysetManager = keysetManager,
    base64Util = base64Util,
    params = tinkDataStoreParams
) {
    override val encryptedKeys: List<String> = listOf(KEY_AUTH_HASH, KEY_SKIN_HASH, KEY_AUTH_INFO)

    suspend fun getAuthHash(): ByteArray = with(dataStore.data.first()) {
        getData(KEY_AUTH_HASH)?.fromBase64() ?: ByteArray(0)
    }

    suspend fun setAuthHash(hash: ByteArray?) = dataStore.edit { prefs ->
        val value = if (hash != null) base64Util.encode(hash) else ""
        prefs.setData(KEY_AUTH_HASH, value)
    }


    suspend fun getSkinHash(): ByteArray = with(dataStore.data.first()) {
        getData(KEY_SKIN_HASH)?.fromBase64() ?: ByteArray(0)
    }

    suspend fun setSkinHash(hash: ByteArray?) = dataStore.edit { prefs ->
        val value = if (hash != null) base64Util.encode(hash) else ""
        prefs.setData(KEY_SKIN_HASH, value)
    }


    val authFlow = dataStore.data.map { prefs ->
        prefs.getData(KEY_AUTH_INFO)?.let { Json.decodeFromString(it) } ?: AuthDto()
    }

    suspend fun setAuthInfo(authDto: AuthDto) = dataStore.edit {
        it.setData(key = KEY_AUTH_INFO, value = Json.encodeToString(authDto))
    }

}

private const val KEY_AUTH_INFO = "auth_info"
private const val KEY_AUTH_HASH = "auth_hash"
private const val KEY_SKIN_HASH = "skin_hash"