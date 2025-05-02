package io.gromif.astracrypt.device_admin.di

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.device_admin_api.AndroidDeviceAdminApi
import io.gromif.device_admin_api.DeviceAdminApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DevicePolicyModule {

    @Singleton
    @Provides
    fun provideDeviceAdminManager(
        @ApplicationContext
        context: Context,
        admin: ComponentName,
        dpm: DevicePolicyManager
    ): DeviceAdminApi = AndroidDeviceAdminApi(
        dpm = dpm,
        admin = admin,
        packageName = context.packageName
    )

    @Provides
    fun provideDevicePolicyManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

}