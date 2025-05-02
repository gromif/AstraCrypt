package io.gromif.astracrypt.device_admin.domain.usecase

import io.gromif.astracrypt.device_admin.domain.model.AdminState
import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService
import kotlinx.coroutines.flow.Flow

class GetAdminStateFlowUseCase(
    private val deviceAdminService: DeviceAdminService
) {

    operator fun invoke(): Flow<AdminState> {
        return deviceAdminService.getAdminStateFlow()
    }

}