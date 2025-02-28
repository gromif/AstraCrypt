package io.gromif.astracrypt.app.di

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.gromif.astracrypt.app.device_admin.DeviceAdminManager
import io.gromif.astracrypt.app.device_admin.DeviceAdminReceiverImpl
import io.gromif.astracrypt.app.device_admin.RequestDeviceAdminContract

@Module
@InstallIn(ViewModelComponent::class)
object DevicePolicyModule {

    @Provides
    fun provideAdminComponentImpl(@ApplicationContext context: Context) =
        ComponentName(context, DeviceAdminReceiverImpl::class.java)

    @Provides
    fun provideDeviceAdminManager(
        adminComponentImpl: ComponentName,
        devicePolicyManager: DevicePolicyManager
    ) = DeviceAdminManager(
        devicePolicyManager = devicePolicyManager,
        adminComponentImpl = adminComponentImpl
    )

    @Provides
    fun provideRequestDeviceAdmin(adminComponentImpl: ComponentName) =
        RequestDeviceAdminContract(adminComponentImpl = adminComponentImpl)

    @Provides
    fun provideDevicePolicyManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

}