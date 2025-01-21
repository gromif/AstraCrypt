package io.gromif.astracrypt.files.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val aeadInfoMapper: Mapper<AeadInfoDto, AeadInfo>,
    private val aeadInfoDtoMapper: Mapper<AeadInfo, AeadInfoDto>
): SettingsRepository {
    private val viewModeKey = intPreferencesKey("view_mode")
    override fun getViewModeFlow(): Flow<ViewMode> {
        return dataStore.data.map {
            val savedViewModeIndex = it[viewModeKey] ?: 0
            ViewMode.entries[savedViewModeIndex]
        }
    }

    override suspend fun setViewMode(viewMode: ViewMode) {
        dataStore.edit { preferences ->
            preferences[viewModeKey] = viewMode.ordinal
        }
    }


    private val aeadInfoKey = stringPreferencesKey("aead_info")
    private var cachedAeadInfo: AeadInfo? = null
    override fun getAeadInfoFlow(): Flow<AeadInfo> {
        return dataStore.data.map {
            val cached = cachedAeadInfo
            if (cached != null) cached else {
                val serializedValue = it[aeadInfoKey]
                val aeadInfo = if (serializedValue != null) {
                    val dto: AeadInfoDto = Json.decodeFromString(serializedValue)
                    aeadInfoMapper(dto)
                } else AeadInfo()
                cachedAeadInfo = aeadInfo
                aeadInfo
            }
        }
    }

    override suspend fun getAeadInfo(): AeadInfo {
        return cachedAeadInfo ?: getAeadInfoFlow().first()
    }

    override suspend fun setAeadInfo(aeadInfo: AeadInfo) {
        cachedAeadInfo = aeadInfo
        val dto: AeadInfoDto = aeadInfoDtoMapper(aeadInfo)
        dataStore.edit { preferences ->
            preferences[aeadInfoKey] = Json.encodeToString(dto)
        }
    }
}