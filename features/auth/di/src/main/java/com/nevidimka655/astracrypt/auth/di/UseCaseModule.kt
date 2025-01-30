package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository
import com.nevidimka655.astracrypt.auth.domain.usecase.DecryptTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetAuthTypeUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintTextUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinTypeUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifySkinUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    @Provides
    fun provideGetAuthUseCase(
        settingsRepository: SettingsRepository,
    ): GetAuthUseCase = GetAuthUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideSetAuthUseCase(
        settingsRepository: SettingsRepository,
    ): SetAuthUseCase = SetAuthUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideGetAuthFlowUseCase(
        settingsRepository: SettingsRepository,
    ): GetAuthFlowUseCase = GetAuthFlowUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideSetHintTextUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
    ): SetHintTextUseCase = SetHintTextUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase
    )

    @Provides
    fun provideSetHintVisibilityUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
    ): SetHintVisibilityUseCase = SetHintVisibilityUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase
    )

    @Provides
    fun provideSetPasswordUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        settingsRepository: SettingsRepository,
        tinkRepository: TinkRepository,
    ): SetAuthTypeUseCase = SetAuthTypeUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        settingsRepository = settingsRepository,
        tinkRepository = tinkRepository
    )

    @Provides
    fun provideSetSkinCalculatorUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        settingsRepository: SettingsRepository,
        tinkRepository: TinkRepository,
    ): SetSkinTypeUseCase = SetSkinTypeUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        settingsRepository = settingsRepository,
        tinkRepository = tinkRepository
    )

    @Provides
    fun provideVerifyCalculatorCombinationUseCase(
        settingsRepository: SettingsRepository,
        tinkRepository: TinkRepository,
    ): VerifySkinUseCase =
        VerifySkinUseCase(settingsRepository = settingsRepository, tinkRepository = tinkRepository)

    @Provides
    fun provideVerifyPasswordUseCase(
        settingsRepository: SettingsRepository,
        tinkRepository: TinkRepository,
    ): VerifyAuthUseCase =
        VerifyAuthUseCase(settingsRepository = settingsRepository, tinkRepository = tinkRepository)

    @Provides
    fun provideSetBindTinkAdUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        tinkRepository: TinkRepository,
    ): SetBindTinkAdUseCase = SetBindTinkAdUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        tinkRepository = tinkRepository
    )

    @Provides
    fun provideDecryptTinkAdUseCase(tinkRepository: TinkRepository): DecryptTinkAdUseCase =
        DecryptTinkAdUseCase(tinkRepository = tinkRepository)

}