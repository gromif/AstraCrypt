package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nevidimka655.astracrypt.features.auth.AuthInfo
import com.nevidimka655.astracrypt.features.profile.Avatars
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.deterministicAeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.sha256
import com.nevidimka655.crypto.tink.extensions.toBase64
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val keysetFactory: KeysetFactory
) {
    private val encryptionSettingsKey = intPreferencesKey("a1")
    suspend fun getEncryptionSettings() = dataStore.data.first()[encryptionSettingsKey] ?: -1
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
    val authInfoFlow = dataStore.data.map { prefs ->
        val key = authInfoKey()
        prefs[key]?.let {
            val authInfoJson = decryptValue(key = key.name, value = it)
            Json.decodeFromString<AuthInfo>(authInfoJson)
        } ?: AuthInfo()
    }
    suspend fun setAuthInfo(authInfo: AuthInfo) = dataStore.edit {
        val key = authInfoKey()
        it[key] = encryptValue(key = key.name, value = Json.encodeToString(authInfo))
    }



    private suspend fun aeadInfoKey() = stringPreferencesKey(encryptKey("b1"))
    val aeadInfoFlow = dataStore.data.map { prefs ->
        val key = aeadInfoKey()
        prefs[key]?.let {
            val aeadInfoJson = decryptValue(key = key.name, value = it)
            Json.decodeFromString<AeadInfo>(aeadInfoJson)
        } ?: AeadInfo()
    }
    suspend fun setEncryptionInfo(aeadInfo: AeadInfo) = dataStore.edit {
        val key = aeadInfoKey()
        it[key] = encryptValue(key = key.name, value = Json.encodeToString(aeadInfo))
    }



    private suspend fun encryptKey(key: String) = getEncryptionTemplate()?.let {
        val aead = keysetFactory.deterministic().deterministicAeadPrimitive()
        aead.encryptDeterministically(key.toByteArray(), key.sha256()).toBase64()
    } ?: key

    private suspend fun encryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetFactory.aead(it).aeadPrimitive()
        aead.encrypt(value.toByteArray(), key.toByteArray()).toBase64()
    } ?: value

    private suspend fun decryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetFactory.aead(it).aeadPrimitive()
        aead.decrypt(value.fromBase64(), key.fromBase64()).decodeToString()
    } ?: value
}