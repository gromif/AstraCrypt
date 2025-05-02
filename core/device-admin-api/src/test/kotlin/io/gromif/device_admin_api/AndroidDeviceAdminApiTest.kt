package io.gromif.device_admin_api

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowDevicePolicyManager

@RunWith(RobolectricTestRunner::class)
class AndroidDeviceAdminApiTest {
    private lateinit var context: Context
    private lateinit var admin: ComponentName
    private lateinit var dpmSpy: DevicePolicyManager
    private lateinit var dpmShadow: ShadowDevicePolicyManager

    private lateinit var androidDeviceAdminApi: AndroidDeviceAdminApi

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        admin = ComponentName(context, AdminReceiver::class.java)

        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        dpmSpy = spyk(dpm)
        dpmShadow = Shadows.shadowOf(dpm)

        androidDeviceAdminApi = AndroidDeviceAdminApi(
            dpm = dpmSpy,
            admin = admin,
            packageName = context.packageName
        )
    }

    @Test
    fun `getTypeFlow should emit ADMIN when admin is active and receiver status is true`() = runTest {
        val adminStatusFlow = MutableStateFlow(true)
        mockkObject(AdminReceiver)
        every { AdminReceiver.receiverAdminStatusFlow } returns adminStatusFlow

        dpmShadow.setActiveAdmin(admin)

        val result = androidDeviceAdminApi.getTypeFlow().first()
        Assert.assertEquals(DeviceAdminApi.Type.ADMIN, result)
    }

    @Test
    fun `getTypeFlow should emit NONE when the app has no active rights`() = runTest {
        val adminStatusFlow = MutableStateFlow<Boolean?>(null)
        mockkObject(AdminReceiver)
        every { AdminReceiver.receiverAdminStatusFlow } returns adminStatusFlow

        dpmShadow.setActiveAdmin(admin)

        val result = androidDeviceAdminApi.getTypeFlow().first()
        Assert.assertEquals(DeviceAdminApi.Type.ADMIN, result)
    }

    @Test
    fun `should use dpm when calling revokeAdminRights`() = runTest {
        dpmShadow.setActiveAdmin(admin)
        androidDeviceAdminApi.revokeAdminRights()
        verify(exactly = 1) { dpmSpy.removeActiveAdmin(admin) }
    }

    @Test
    fun `hasAdminRights should return true when admin rights are granted`() = runTest {
        dpmShadow.setActiveAdmin(admin)
        Assert.assertTrue(androidDeviceAdminApi.hasAdminRights())
        verify(exactly = 1) { dpmSpy.isAdminActive(admin) }
    }

    @Test
    fun `hasProfileRights should return true when profile rights are granted`() = runTest {
        dpmShadow.setProfileOwner(admin)
        Assert.assertTrue(androidDeviceAdminApi.hasProfileRights())
        verify(exactly = 1) { dpmSpy.isProfileOwnerApp(context.packageName) }
    }

    @Test
    fun `hasDeviceRights should return true when device rights are granted`() = runTest {
        dpmShadow.setDeviceOwner(admin)
        Assert.assertTrue(androidDeviceAdminApi.hasDeviceRights())
        verify(exactly = 1) { dpmSpy.isDeviceOwnerApp(context.packageName) }
    }

}