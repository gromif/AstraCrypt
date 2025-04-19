package io.gromif.astracrypt.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.SetLastActiveTimeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAeadModeFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.DecryptTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetAeadModeUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetBindTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.hint.SetHintTextUseCase
import io.gromif.astracrypt.auth.domain.usecase.hint.SetHintVisibilityUseCase
import io.gromif.astracrypt.auth.domain.usecase.skin.SetSkinTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.skin.VerifySkinUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.CheckAuthTimeoutUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.SetTimeoutUseCase

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
    fun provideSetTimeoutUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
    ) = SetTimeoutUseCase(getAuthUseCase = getAuthUseCase, setAuthUseCase = setAuthUseCase)

    @ViewModelScoped
    @Provides
    fun provideSetLastActiveTimeUseCase(clockService: ClockService) =
        SetLastActiveTimeUseCase(clockService = clockService)

    @ViewModelScoped
    @Provides
    fun provideCheckAuthTimeoutUseCase(
        getAuthUseCase: GetAuthUseCase,
        clockService: ClockService
    ) = CheckAuthTimeoutUseCase(
        getAuthUseCase = getAuthUseCase,
        clockService = clockService
    )

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