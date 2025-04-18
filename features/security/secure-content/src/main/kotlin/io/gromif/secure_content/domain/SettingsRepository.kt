package io.gromif.secure_content.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSecureContentModeFlow(): Flow<SecureContentMode>

    suspend fun setSecureContentMode(mode: SecureContentMode)

}