package com.nevidimka655.astracrypt.auth.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.crypto.tink.core.TinkDataStore
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.fromBase64
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    private val base64Util: Base64Util,
) : TinkDataStore(base64Util = base64Util) {

    private suspend fun authHashKey() = stringPreferencesKey(hashKey("auth_hash"))
    private val authHashFlow = dataStore.data.map {
        val hash = it[authHashKey()]
        if (!hash.isNullOrEmpty()) hash.fromBase64()
        else ByteArray(0)
    }

    suspend fun getAuthHash() = authHashFlow.first()

    suspend fun setAuthHash(hash: ByteArray?) = dataStore.edit { preferences ->
        preferences[authHashKey()] = if (hash != null) base64Util.encode(hash) else ""
    }


    private suspend fun skinHashKey() = stringPreferencesKey(hashKey("skin_hash"))
    private val skinHashFlow = dataStore.data.map {
        val hash = it[skinHashKey()]
        if (!hash.isNullOrEmpty()) hash.fromBase64()
        else ByteArray(0)
    }

    suspend fun getSkinHash() = skinHashFlow.first()

    suspend fun setSkinHash(hash: ByteArray?) = dataStore.edit { preferences ->
        preferences[skinHashKey()] = if (hash != null) base64Util.encode(hash) else ""
    }


    private suspend fun authInfoKey() = stringPreferencesKey(hashKey("auth_info"))
    val authFlow = dataStore.data.map { prefs ->
        val key = authInfoKey()
        prefs[key]?.let {
            val authInfoJson = decrypt(key = key.name, value = it)
            Json.decodeFromString<AuthDto>(authInfoJson)
        } ?: AuthDto()
    }

    suspend fun setAuthInfo(authDto: AuthDto) = dataStore.edit {
        val key = authInfoKey()
        it[key] = encrypt(key = key.name, value = Json.encodeToString(authDto))
    }


    private val aeadKey = intPreferencesKey("aead")
    override suspend fun getAeadTemplate(): KeysetTemplates.AEAD? {
        return KeysetTemplates.AEAD.entries.getOrNull(
            dataStore.data.first()[aeadKey] ?: defaultAeadTemplateIndex
        )
    }

    override suspend fun createKeyPrfKeyset(): KeysetHandle? {
        return if (getAeadTemplate() != null) keysetManager.getKeyset(
            tag = "ds_auth_key",
            associatedData = "ds_auth_key_ad".toByteArray(),
            keyParams = KeysetTemplates.PRF.HKDF_SHA256.params
        ) else null
    }

    override suspend fun createValueAeadKeyset(): KeysetHandle? {
        return getAeadTemplate()?.let {
            keysetManager.getKeyset(
                tag = "ds_auth_value",
                associatedData = "ds_auth_value_ad".toByteArray(),
                keyParams = it.params
            )
        }
    }

}