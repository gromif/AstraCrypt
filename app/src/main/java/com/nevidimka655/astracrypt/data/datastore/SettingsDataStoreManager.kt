package com.nevidimka655.astracrypt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.astracrypt.domain.model.profile.Avatars
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.crypto.tink.core.TinkDataStore
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsDataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    base64Util: Base64Util
): TinkDataStore(base64Util = base64Util) {

    private suspend fun profileInfoKey() = stringPreferencesKey(hashKey("a2"))
    val profileInfoFlow = dataStore.data.map { prefs ->
        val key = profileInfoKey()
        prefs[key]?.let {
            val profileInfoJson = decrypt(key = key.name, value = it)
            Json.decodeFromString<ProfileInfo>(profileInfoJson)
        } ?: ProfileInfo(defaultAvatar = Avatars.AVATAR_5)
    }
    suspend fun setProfileInfo(profileInfo: ProfileInfo) = dataStore.edit {
        val key = profileInfoKey()
        it[key] = encrypt(key = key.name, value = Json.encodeToString(profileInfo))
    }



    private val aeadKey = intPreferencesKey("aead")
    override suspend fun getAeadTemplate(): KeysetTemplates.AEAD? {
        return KeysetTemplates.AEAD.entries.getOrNull(
            dataStore.data.first()[aeadKey] ?: defaultAeadTemplateIndex
        )
    }

    override suspend fun createKeyPrfKeyset(): KeysetHandle? {
        return if (getAeadTemplate() != null) keysetManager.getKeyset(
            tag = "ds_settings_key", keyParams = KeysetTemplates.PRF.HKDF_SHA256.params
        ) else null
    }

    override suspend fun createValueAeadKeyset(): KeysetHandle? {
        return getAeadTemplate()?.let {
            keysetManager.getKeyset(tag = "ds_settings_value", keyParams = it.params)
        }
    }
}