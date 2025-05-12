package io.gromif.notes.domain.repository

import io.gromif.notes.domain.model.AeadMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAeadModeFlow(): Flow<AeadMode>

    suspend fun getAeadMode(): AeadMode

    suspend fun setAeadMode(aeadMode: AeadMode)

}