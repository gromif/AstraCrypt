package com.nevidimka655.astracrypt.app.di

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.nevidimka655.astracrypt.app.utils.DeviceAdminManager
import com.nevidimka655.astracrypt.app.utils.contracts.RequestDeviceAdmin
import com.nevidimka655.astracrypt.app.utils.receivers.DeviceAdminImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DevicePolicyModule {

    @Provides
    fun provideAdminComponentImpl(@ApplicationContext context: Context) =
        ComponentName(context, DeviceAdminImpl::class.java)

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
        RequestDeviceAdmin(adminComponentImpl = adminComponentImpl)

    @Provides
    fun provideDevicePolicyManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

}