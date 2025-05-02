package io.gromif.astracrypt.device_admin.data

import io.gromif.astracrypt.device_admin.domain.model.AdminState
import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService
import io.gromif.device_admin_api.DeviceAdminApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeviceAdminServiceImpl(
    private val deviceAdminApi: DeviceAdminApi
): DeviceAdminService {
    override fun getAdminStateFlow(): Flow<AdminState> = deviceAdminApi.getTypeFlow().map {
        AdminState(isActive = it != DeviceAdminApi.Type.NONE)
    }

    override suspend fun revokeAdmin() {
        deviceAdminApi.revokeAdminRights()
    }
}