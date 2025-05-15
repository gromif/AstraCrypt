package io.gromif.astracrypt.auth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.gromif.astracrypt.auth.data.dto.AuthDto
import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
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
    private val encoder: Encoder,
    keysetManager: KeysetManager,
    tinkDataStoreParams: Params,
    private val authMapper: Mapper<AuthDto, Auth>,
    private val authDtoMapper: Mapper<Auth, AuthDto>,
) : SettingsRepository, TinkDataStore(
    dataStore = dataStore,
    keysetManager = keysetManager,
    encoder = encoder,
    params = tinkDataStoreParams
) {
    private val authKey = tinkPreference("auth_info")
    private var cachedAuth: Auth? = null
    override val authFlow: Flow<Auth> = dataStore.data.map { prefs ->
        cachedAuth ?: prefs.getData(authKey.name)?.let {
            val dto: AuthDto = Json.decodeFromString(it)
            authMapper(dto).also { cachedAuth = it }
        } ?: Auth()
    }

    override suspend fun setAuth(auth: Auth) {
        cachedAuth = auth
        dataStore.edit {
            val dto = authDtoMapper(auth)
            it.setData(key = authKey.name, value = Json.encodeToString(dto))
        }
    }

    override suspend fun getAuth(): Auth {
        return cachedAuth ?: authFlow.first()
    }

    private val authHashKey = tinkPreference("auth_hash")
    override suspend fun getAuthHash(): ByteArray {
        return with(dataStore.data.first()) {
            getData(authHashKey.name)?.let {
                encoder.decode(it)
            } ?: ByteArray(0)
        }
    }

    override suspend fun setAuthHash(hash: ByteArray?) {
        dataStore.edit { prefs ->
            val value = hash?.let { encoder.encode(it) } ?: ""
            prefs.setData(authHashKey.name, value)
        }
    }

    private val skinHashKey = tinkPreference("skin_hash")
    override suspend fun getSkinHash(): ByteArray {
        return with(dataStore.data.first()) {
            getData(skinHashKey.name)?.let {
                encoder.decode(it)
            } ?: ByteArray(0)
        }
    }

    override suspend fun setSkinHash(hash: ByteArray?) {
        dataStore.edit { prefs ->
            val value = hash?.let { encoder.encode(it) } ?: ""
            prefs.setData(skinHashKey.name, value)
        }
    }

    override suspend fun setAead(aeadMode: AeadMode) {
        val aead = KeysetTemplates.AEAD.entries.getOrNull(aeadMode.id)
        setTinkDataStoreAead(aead)
    }

    override fun getAeadFlow(): Flow<AeadMode> {
        return getTinkDataStoreAeadFlow().map { aead ->
            aead?.let {
                AeadMode.Template(id = it.ordinal)
            } ?: AeadMode.None
        }
    }
}
