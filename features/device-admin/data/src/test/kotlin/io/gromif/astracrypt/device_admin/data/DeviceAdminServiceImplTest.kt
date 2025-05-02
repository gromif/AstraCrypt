package io.gromif.astracrypt.device_admin.data

import io.gromif.device_admin_api.DeviceAdminApi
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeviceAdminServiceImplTest {
    private lateinit var deviceAdminServiceImpl: DeviceAdminServiceImpl
    private val deviceAdminApi: DeviceAdminApi = mockk()

    @Before
    fun setUp() {
        deviceAdminServiceImpl = DeviceAdminServiceImpl(deviceAdminApi)
    }

    @Test
    fun `should call the correct API when calling revokeAdmin`() = runTest {
        coJustRun { deviceAdminApi.revokeAdminRights() }

        deviceAdminServiceImpl.revokeAdmin()

        coVerify(exactly = 1) { deviceAdminApi.revokeAdminRights() }
    }
}