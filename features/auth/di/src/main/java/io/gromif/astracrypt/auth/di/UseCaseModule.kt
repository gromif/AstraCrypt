package io.gromif.astracrypt.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.DecryptTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.GetAeadModeFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetAeadModeUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetAuthTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetHintTextUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetSkinTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.VerifySkinUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetAuthUseCase(
        settingsRepository: SettingsRepository,
    ): GetAuthUseCase = GetAuthUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAuthUseCase(
        settingsRepository: SettingsRepository,
    ): SetAuthUseCase = SetAuthUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetAuthFlowUseCase(
        settingsRepository: SettingsRepository,
    ): GetAuthFlowUseCase = GetAuthFlowUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetHintTextUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
    ): SetHintTextUseCase = SetHintTextUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideSetHintVisibilityUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
    ): SetHintVisibilityUseCase = SetHintVisibilityUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideSetPasswordUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        settingsRepository: SettingsRepository,
        tinkService: TinkService,
    ): SetAuthTypeUseCase = SetAuthTypeUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        settingsRepository = settingsRepository,
        tinkService = tinkService
    )

    @ViewModelScoped
    @Provides
    fun provideSetSkinCalculatorUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        settingsRepository: SettingsRepository,
        tinkService: TinkService,
    ): SetSkinTypeUseCase = SetSkinTypeUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        settingsRepository = settingsRepository,
        tinkService = tinkService
    )

    @ViewModelScoped
    @Provides
    fun provideVerifyCalculatorCombinationUseCase(
        settingsRepository: SettingsRepository,
        tinkService: TinkService,
    ): VerifySkinUseCase =
        VerifySkinUseCase(settingsRepository = settingsRepository, tinkService = tinkService)

    @ViewModelScoped
    @Provides
    fun provideVerifyPasswordUseCase(
        settingsRepository: SettingsRepository,
        tinkService: TinkService,
    ): VerifyAuthUseCase =
        VerifyAuthUseCase(settingsRepository = settingsRepository, tinkService = tinkService)

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
    fun provideDecryptTinkAdUseCase(tinkService: TinkService): DecryptTinkAdUseCase =
        DecryptTinkAdUseCase(tinkService = tinkService)

    @ViewModelScoped
    @Provides
    fun provideGetAeadModeFlowUseCase(
        settingsRepository: SettingsRepository,
    ): GetAeadModeFlowUseCase = GetAeadModeFlowUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAeadModeUseCase(
        settingsRepository: SettingsRepository,
    ): SetAeadModeUseCase = SetAeadModeUseCase(settingsRepository = settingsRepository)

}