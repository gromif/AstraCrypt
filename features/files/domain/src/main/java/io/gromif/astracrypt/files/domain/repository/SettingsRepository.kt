package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.ViewMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getViewModeFlow(): Flow<ViewMode>

    suspend fun setViewMode(viewMode: ViewMode)

}
