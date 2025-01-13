package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.ViewMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAeadNameFlow(): Flow<String?>

    suspend fun getAeadTemplateIndex(): Int

    suspend fun setAeadTemplateIndex(aead: Int)


    fun getViewModeFlow(): Flow<ViewMode>

    suspend fun setViewMode(viewMode: ViewMode)

}