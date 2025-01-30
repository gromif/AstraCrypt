package io.gromif.astracrypt.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.domain.KeysetTemplates
import io.gromif.crypto.tink.encoders.Base64Encoder
import io.gromif.tink_datastore.TinkDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val profileMapper: Mapper<ProfileDto, Profile>,
    private val profileDtoMapper: Mapper<Profile, ProfileDto>,
    keysetManager: KeysetManager,
    tinkDataStoreParams: Params,
    base64Encoder: Base64Encoder,
) : SettingsRepository, TinkDataStore(
    dataStore = dataStore,
    keysetManager = keysetManager,
    encoder = base64Encoder,
    params = tinkDataStoreParams
) {
    override val encryptedKeys: List<String> = listOf(KEY_PROFILE_INFO)

    private var cachedProfile: Profile? = null
    override val profileFlow: Flow<Profile> = dataStore.data.map { prefs ->
        cachedProfile ?: prefs.getData(KEY_PROFILE_INFO)?.let {
            val dto: ProfileDto = Json.Default.decodeFromString(it)
            profileMapper(dto)
        } ?: Profile()
    }

    override suspend fun getProfile(): Profile = profileFlow.first()

    override suspend fun setProfile(profile: Profile) {
        cachedProfile = profile
        val dto = profileDtoMapper(profile)
        val json = Json.Default.encodeToString(dto)
        dataStore.edit {
            it.setData(key = KEY_PROFILE_INFO, value = json)
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
        val targetAead = KeysetTemplates.AEAD.entries.getOrNull(aead)
        setTinkDataStoreAead(targetAead = targetAead)
    }
}

private const val KEY_PROFILE_INFO = "profile_info"
private const val KEY_AVATAR_AEAD = "avatar_aead"