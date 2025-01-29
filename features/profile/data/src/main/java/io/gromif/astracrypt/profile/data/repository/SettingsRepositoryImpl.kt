package io.gromif.astracrypt.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.core.TinkDataStore
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    private val profileMapper: Mapper<ProfileDto, Profile>,
    private val profileDtoMapper: Mapper<Profile, ProfileDto>,
    base64Util: Base64Util,
) : SettingsRepository, TinkDataStore(base64Util = base64Util) {

    private suspend fun profileInfoKey() = stringPreferencesKey(hashKey(KEY_PROFILE_INFO))

    private var cachedProfile: Profile? = null
    override val profileFlow: Flow<Profile>
        get() = dataStore.data.map { prefs ->
            cachedProfile ?: prefs[profileInfoKey()]?.let {
                val json = decrypt(key = KEY_PROFILE_INFO, value = it)
                val dto: ProfileDto = Json.Default.decodeFromString(json)
                profileMapper(dto)
            } ?: Profile()
        }

    override suspend fun getProfile(): Profile = profileFlow.first()

    override suspend fun setProfile(profile: Profile) {
        cachedProfile = profile
        val dto = profileDtoMapper(profile)
        val json = Json.Default.encodeToString(dto)
        dataStore.edit {
            it[profileInfoKey()] = encrypt(key = KEY_PROFILE_INFO, value = json)
        }
    }



    private val avatarAeadKey = intPreferencesKey(KEY_AVATAR_AEAD)
    override suspend fun getAvatarAead(): Int {
        return dataStore.data.first()[avatarAeadKey] ?: -1
    }

    override suspend fun setAvatarAead(aead: Int) {
        dataStore.edit { it[avatarAeadKey] = aead }
    }

    override suspend fun setAead(aead: Int) {
        TODO("Not yet implemented")
    }

    private val aeadKey = intPreferencesKey("aead")
    override suspend fun getAeadTemplate(): KeysetTemplates.AEAD? {
        return KeysetTemplates.AEAD.entries.getOrNull(
            dataStore.data.first()[aeadKey] ?: defaultAeadTemplateIndex
        )
    }

    override suspend fun createKeyPrfKeyset(): KeysetHandle? {
        return if (getAeadTemplate() != null) keysetManager.getKeyset(
            tag = TINK_KEY_TAG,
            associatedData = TINK_KEY_AD.toByteArray(),
            keyParams = KeysetTemplates.PRF.HKDF_SHA256.params
        ) else null
    }

    override suspend fun createValueAeadKeyset(): KeysetHandle? {
        return getAeadTemplate()?.let {
            keysetManager.getKeyset(
                tag = TINK_VALUE_TAG,
                associatedData = TINK_VALUE_AD.toByteArray(),
                keyParams = it.params
            )
        }
    }
}

private const val KEY_PROFILE_INFO = "profile_info"
private const val KEY_AVATAR_AEAD = "avatar_aead"

private const val TINK_KEY_TAG = "ds_profile_key"
private const val TINK_KEY_AD = "${TINK_KEY_TAG}_ad"
private const val TINK_VALUE_TAG = "ds_profile_value"
private const val TINK_VALUE_AD = "${TINK_VALUE_TAG}_ad"