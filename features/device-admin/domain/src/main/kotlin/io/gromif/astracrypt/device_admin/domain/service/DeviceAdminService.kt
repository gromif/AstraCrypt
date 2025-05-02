package io.gromif.astracrypt.device_admin.domain.service

import io.gromif.astracrypt.device_admin.domain.model.AdminState
import kotlinx.coroutines.flow.Flow

interface DeviceAdminService {

    fun getAdminStateFlow(): Flow<AdminState>

    suspend fun revokeAdmin()

}