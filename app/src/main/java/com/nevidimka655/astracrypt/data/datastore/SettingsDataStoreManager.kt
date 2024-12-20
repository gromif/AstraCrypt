package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.domain.model.auth.Auth
import com.nevidimka655.astracrypt.domain.model.profile.Avatars
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.crypto.tink.core.encoders.Base64Service
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.deterministicAeadPrimitive
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val KEYSET_TAG_KEY = "3Bd~43?k"
private const val KEYSET_TAG_VALUE = "l0_ShH%OLq"

class SettingsDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    private val sha256Service: Sha256Service,
    private val base64Service: Base64Service
) {
    private val encryptionSettingsKey = intPreferencesKey("a1")
    suspend fun getEncryptionSettings() = dataStore.data.first()[encryptionSettingsKey] ?: 0
    private suspend fun getEncryptionTemplate() = getEncryptionSettings().let {
        if (it > -1) KeysetTemplates.AEAD.entries[it]
        else null
    }



    private suspend fun profileInfoKey() = stringPreferencesKey(encryptKey("a2"))
    val profileInfoFlow = dataStore.data.map { prefs ->
        val key = profileInfoKey()
        prefs[key]?.let {
            val profileInfoJson = decryptValue(key = key.name, value = it)
            Json.decodeFromString<ProfileInfo>(profileInfoJson)
        } ?: ProfileInfo(defaultAvatar = Avatars.AVATAR_5)
    }
    suspend fun setProfileInfo(profileInfo: ProfileInfo) = dataStore.edit {
        val key = profileInfoKey()
        it[key] = encryptValue(key = key.name, value = Json.encodeToString(profileInfo))
    }



    private suspend fun authInfoKey() = stringPreferencesKey(encryptKey("a3"))
    val authFlow = dataStore.data.map { prefs ->
        val key = authInfoKey()
        prefs[key]?.let {
            val authInfoJson = decryptValue(key = key.name, value = it)
            Json.decodeFromString<Auth>(authInfoJson)
        } ?: Auth()
    }
    suspend fun setAuthInfo(auth: Auth) = dataStore.edit {
        val key = authInfoKey()
        it[key] = encryptValue(key = key.name, value = Json.encodeToString(auth))
    }



    private suspend fun aeadInfoKey() = stringPreferencesKey(encryptKey("b1"))
    val aeadInfoFlow = dataStore.data.map { prefs ->
        val key = aeadInfoKey()
        prefs[key]?.let {
            val aeadInfoJson = decryptValue(key = key.name, value = it)
            Json.decodeFromString<AeadInfo>(aeadInfoJson)
        } ?: AeadInfo()
    }
    suspend fun setAeadInfo(aeadInfo: AeadInfo) = dataStore.edit {
        val key = aeadInfoKey()
        it[key] = encryptValue(key = key.name, value = Json.encodeToString(aeadInfo))
    }



    private suspend fun encryptKey(key: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_KEY,
            keyParams = KeysetTemplates.DeterministicAEAD.AES256_SIV.params
        ).deterministicAeadPrimitive()
        val associatedData = sha256Service.compute(value = key.toByteArray())
        val encryptedBytes = aead.encryptDeterministically(key.toByteArray(), associatedData)
        base64Service.encode(bytes = encryptedBytes)
    } ?: key

    private suspend fun encryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_VALUE,
            keyParams = it.params
        ).aeadPrimitive()
        val encryptedBytes = aead.encrypt(value.toByteArray(), key.toByteArray())
        base64Service.encode(bytes = encryptedBytes)
    } ?: value

    private suspend fun decryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_VALUE,
            keyParams = it.params
        ).aeadPrimitive()
        val encryptedBytes = base64Service.decode(value = value)
        aead.decrypt(encryptedBytes, key.toByteArray()).decodeToString()
    } ?: value
}