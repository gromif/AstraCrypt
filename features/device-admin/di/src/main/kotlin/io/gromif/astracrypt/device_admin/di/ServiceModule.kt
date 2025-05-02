package io.gromif.astracrypt.device_admin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.device_admin.data.DeviceAdminServiceImpl
import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService
import io.gromif.device_admin_api.DeviceAdminApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Singleton
    @Provides
    fun getDeviceAdminService(deviceAdminApi: DeviceAdminApi): DeviceAdminService =
        DeviceAdminServiceImpl(deviceAdminApi = deviceAdminApi)

}