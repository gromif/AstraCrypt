package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ViewMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getViewModeFlow(): Flow<ViewMode>

    suspend fun setViewMode(viewMode: ViewMode)


    fun getAeadInfoFlow(): Flow<AeadInfo>

    suspend fun getAeadInfo(): AeadInfo

    suspend fun setAeadInfo(aeadInfo: AeadInfo)

}