package io.gromif.astracrypt.device_admin.presentation

import io.gromif.astracrypt.device_admin.domain.model.AdminState
import io.gromif.astracrypt.device_admin.domain.usecase.GetAdminStateFlowUseCase
import io.gromif.astracrypt.device_admin.domain.usecase.RevokeAdminUseCase
import io.gromif.astracrypt.device_admin.presentation.contracts.RequestDeviceAdminContract
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AdminSettingsViewModelTest {
    private lateinit var vm: AdminSettingsViewModel
    private val defaultDispatcher = UnconfinedTestDispatcher()
    private val revokeAdminUseCase: RevokeAdminUseCase = mockk()
    private val requestDeviceAdminContract: RequestDeviceAdminContract = mockk()
    private val getAdminStateFlowUseCase: GetAdminStateFlowUseCase = mockk()

    private val targetAdminState = AdminState(isActive = true)

    @Before
    fun setUp() {
        every { getAdminStateFlowUseCase() } returns flow { targetAdminState }

        vm = AdminSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            revokeAdminUseCase = revokeAdminUseCase,
            requestDeviceAdminContract = requestDeviceAdminContract,
            getAdminStateFlowUseCase = getAdminStateFlowUseCase
        )
    }

    @Test
    fun `should call revokeAdminUseCase when calling disable`() = runTest {
        coJustRun { revokeAdminUseCase() }

        vm.disable()

        coVerify(exactly = 1) { revokeAdminUseCase() }
    }
}