package io.gromif.astracrypt.device_admin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.device_admin.domain.service.DeviceAdminService
import io.gromif.astracrypt.device_admin.domain.usecase.GetAdminStateFlowUseCase
import io.gromif.astracrypt.device_admin.domain.usecase.RevokeAdminUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun getAdminStateFlowUseCase(deviceAdminService: DeviceAdminService) =
        GetAdminStateFlowUseCase(deviceAdminService)

    @ViewModelScoped
    @Provides
    fun revokeAdminUseCase(deviceAdminService: DeviceAdminService) =
        RevokeAdminUseCase(deviceAdminService)

}