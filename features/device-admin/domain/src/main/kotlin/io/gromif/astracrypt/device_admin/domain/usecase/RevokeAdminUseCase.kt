package io.gromif.astracrypt.device_admin.domain.usecase

import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService

class RevokeAdminUseCase(
    private val deviceAdminService: DeviceAdminService
) {

    suspend operator fun invoke() {
        deviceAdminService.revokeAdmin()
    }

}