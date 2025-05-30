package io.gromif.astracrypt.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.domain.model.AeadMode
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.astracrypt.utils.Mapper
import io.gromif.crypto.tink.core.encoders.Encoder
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.crypto.tink.keyset.KeysetTemplates
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
    encoder: Encoder,
) : SettingsRepository, TinkDataStore(
    dataStore = dataStore,
    keysetManager = keysetManager,
    encoder = encoder,
    params = tinkDataStoreParams
) {
    private val profileKey = tinkPreference("profile_info")
    private var cachedProfile: Profile? = null
    override val profileFlow: Flow<Profile> = dataStore.data.map { prefs ->
        cachedProfile ?: prefs.getData(profileKey.name)?.let {
            val dto: ProfileDto = Json.Default.decodeFromString(it)
            profileMapper(dto).also { cachedProfile = it }
        } ?: Profile()
    }

    override suspend fun getProfile(): Profile = profileFlow.first()

    override suspend fun setProfile(profile: Profile) {
        cachedProfile = profile
        val dto = profileDtoMapper(profile)
        val json = Json.Default.encodeToString(dto)
        dataStore.edit {
            it.setData(key = profileKey.name, value = json)
        }
    }


    private val avatarAeadKey = intPreferencesKey(KEY_AVATAR_AEAD)
    override suspend fun getAvatarAead(): Int {
        return dataStore.data.first()[avatarAeadKey] ?: -1
    }

    override suspend fun setAvatarAead(aead: Int) {
        dataStore.edit { it[avatarAeadKey] = aead }
    }

    override suspend fun setAead(aeadMode: AeadMode) {
        val targetAead = KeysetTemplates.AEAD.entries.getOrNull(aeadMode.id)
        setTinkDataStoreAead(targetAead = targetAead)
    }

    override fun getAeadFlow(): Flow<AeadMode> {
        return getTinkDataStoreAeadFlow().map {
            it?.let { AeadMode.Template(id = it.ordinal) } ?: AeadMode.None
        }
    }
}

private const val KEY_AVATAR_AEAD = "avatar_aead"