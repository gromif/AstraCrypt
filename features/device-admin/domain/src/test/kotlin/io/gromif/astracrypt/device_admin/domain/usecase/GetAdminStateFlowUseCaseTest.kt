package io.gromif.astracrypt.device_admin.domain.usecase

import io.gromif.astracrypt.device_admin.domain.model.AdminState
import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAdminStateFlowUseCaseTest {
    private lateinit var getAdminStateFlowUseCase: GetAdminStateFlowUseCase
    private val deviceAdminService: DeviceAdminService = mockk()

    @Before
    fun setUp() {
        getAdminStateFlowUseCase = GetAdminStateFlowUseCase(deviceAdminService)
    }

    @Test
    fun `should return the correct admin state flow`() = runTest {
        val targetAdminStateFlow: Flow<AdminState> = mockk()

        every { deviceAdminService.getAdminStateFlow() } returns targetAdminStateFlow

        val adminStateFlow = getAdminStateFlowUseCase()
        Assert.assertEquals(targetAdminStateFlow, adminStateFlow)

        verify(exactly = 1) { deviceAdminService.getAdminStateFlow() }
    }
}