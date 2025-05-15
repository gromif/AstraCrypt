package io.gromif.astracrypt.auth.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAeadModeFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.DecryptTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetAeadModeUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetBindTinkAdUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object EncryptionUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideSetBindTinkAdUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        tinkService: TinkService,
    ): SetBindTinkAdUseCase = SetBindTinkAdUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        tinkService = tinkService
    )

    @ViewModelScoped
    @Provides
    fun provideDecryptTinkAdUseCase(tinkService: TinkService) =
        DecryptTinkAdUseCase(tinkService = tinkService)

    @ViewModelScoped
    @Provides
    fun provideGetAeadModeFlowUseCase(settingsRepository: SettingsRepository) =
        GetAeadModeFlowUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAeadModeUseCase(settingsRepository: SettingsRepository) =
        SetAeadModeUseCase(settingsRepository = settingsRepository)
}