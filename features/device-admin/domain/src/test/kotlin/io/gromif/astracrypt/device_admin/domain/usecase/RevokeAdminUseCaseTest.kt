package io.gromif.astracrypt.device_admin.domain.usecase

import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RevokeAdminUseCaseTest {
    private lateinit var revokeAdminUseCase: RevokeAdminUseCase
    private val deviceAdminService: DeviceAdminService = mockk()

    @Before
    fun setUp() {
        revokeAdminUseCase = RevokeAdminUseCase(deviceAdminService)
    }

    @Test
    fun `should properly revoke admin rights`() = runTest {
        coJustRun { deviceAdminService.revokeAdmin() }

        revokeAdminUseCase()

        coVerify(exactly = 1) { deviceAdminService.revokeAdmin() }
    }
}