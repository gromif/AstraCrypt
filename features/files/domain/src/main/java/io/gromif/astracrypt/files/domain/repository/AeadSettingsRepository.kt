package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.AeadInfo
import kotlinx.coroutines.flow.Flow

interface AeadSettingsRepository {

    fun getAeadInfoFlow(): Flow<AeadInfo>

    suspend fun getAeadInfo(): AeadInfo

    suspend fun setAeadInfo(aeadInfo: AeadInfo)
}
