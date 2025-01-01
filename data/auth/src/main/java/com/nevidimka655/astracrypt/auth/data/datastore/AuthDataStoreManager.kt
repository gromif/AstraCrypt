package com.nevidimka655.astracrypt.auth.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.prf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    private val base64Util: Base64Util
) {
    private val encryptionSettingsKey = intPreferencesKey("encryption")
    suspend fun getEncryptionSettings() = dataStore.data.first()[encryptionSettingsKey] ?: 0
    private suspend fun getEncryptionTemplate() = getEncryptionSettings().let {
        KeysetTemplates.AEAD.entries.getOrNull(it)
    }



    private suspend fun passwordHashKey() = stringPreferencesKey(hashKey("password_hash"))
    val passwordHashFlow = dataStore.data.map {
        val hash = it[passwordHashKey()]
        if (!hash.isNullOrEmpty()) hash.fromBase64()
        else ByteArray(0)
    }
    suspend fun getPasswordHash() = passwordHashFlow.first()

    suspend fun setPasswordHash(hash: ByteArray?) = dataStore.edit { preferences ->
        preferences[passwordHashKey()] = if (hash != null) base64Util.encode(hash) else ""
    }



    private suspend fun authInfoKey() = stringPreferencesKey(hashKey("auth_info"))
    val authFlow = dataStore.data.map { prefs ->
        val key = authInfoKey()
        prefs[key]?.let {
            val authInfoJson = decryptValue(key = key.name, value = it)
            Json.decodeFromString<AuthDto>(authInfoJson)
        } ?: AuthDto()
    }
    suspend fun setAuthInfo(authDto: AuthDto) = dataStore.edit {
        val key = authInfoKey()
        it[key] = encryptValue(key = key.name, value = Json.encodeToString(authDto))
    }



    private suspend fun hashKey(key: String) = getEncryptionTemplate()?.let {
        val prf = keysetManager.getKeyset(
            tag = KEYSET_TAG_KEY,
            associatedData = KEYSET_TAG_KEY_AD,
            keyParams = KeysetTemplates.PRF.HKDF_SHA256.params
        ).prf()
        val prfBytes = prf.computePrimary(key.toByteArray(), 32)
        base64Util.encode(bytes = prfBytes)
    } ?: key

    private suspend fun encryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_VALUE,
            associatedData = KEYSET_TAG_VALUE_AD,
            keyParams = it.params
        ).aeadPrimitive()
        val encryptedBytes = aead.encrypt(value.toByteArray(), key.toByteArray())
        base64Util.encode(bytes = encryptedBytes)
    } ?: value

    private suspend fun decryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_VALUE,
            associatedData = KEYSET_TAG_VALUE_AD,
            keyParams = it.params
        ).aeadPrimitive()
        val encryptedBytes = base64Util.decode(value = value)
        aead.decrypt(encryptedBytes, key.toByteArray()).decodeToString()
    } ?: value

}

private const val KEYSET_TAG_KEY = "E5[^b*ea"
private val KEYSET_TAG_KEY_AD = "1]uT2Ev]".toByteArray()

private const val KEYSET_TAG_VALUE = "D,:px1>W"
private val KEYSET_TAG_VALUE_AD = "<C4fW,lW".toByteArray()