package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.domain.model.profile.Avatars
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.core.hash.Sha256Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.deterministicAead
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val KEYSET_TAG_KEY = "3Bd~43?k"
private const val KEYSET_TAG_VALUE = "l0_ShH%OLq"

class SettingsDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    private val sha256Util: Sha256Util,
    private val base64Util: Base64Util
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
        ).deterministicAead()
        val associatedData = sha256Util.compute(value = key.toByteArray())
        val encryptedBytes = aead.encryptDeterministically(key.toByteArray(), associatedData)
        base64Util.encode(bytes = encryptedBytes)
    } ?: key

    private suspend fun encryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_VALUE,
            keyParams = it.params
        ).aeadPrimitive()
        val encryptedBytes = aead.encrypt(value.toByteArray(), key.toByteArray())
        base64Util.encode(bytes = encryptedBytes)
    } ?: value

    private suspend fun decryptValue(key: String, value: String) = getEncryptionTemplate()?.let {
        val aead = keysetManager.getKeyset(
            tag = KEYSET_TAG_VALUE,
            keyParams = it.params
        ).aeadPrimitive()
        val encryptedBytes = base64Util.decode(value = value)
        aead.decrypt(encryptedBytes, key.toByteArray()).decodeToString()
    } ?: value
}