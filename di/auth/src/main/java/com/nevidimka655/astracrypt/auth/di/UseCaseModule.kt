package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.TinkRepository
import com.nevidimka655.astracrypt.auth.domain.usecase.DisableAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintTextUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetPasswordUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinDefaultUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifySkinUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    @Provides
    fun provideDisableAuthUseCase(
        repository: Repository
    ): DisableAuthUseCase = DisableAuthUseCase(repository = repository)

    @Provides
    fun provideGetAuthFlowUseCase(
        repository: Repository
    ): GetAuthFlowUseCase = GetAuthFlowUseCase(repository = repository)

    @Provides
    fun provideSetHintTextUseCase(
        repository: Repository
    ): SetHintTextUseCase = SetHintTextUseCase(repository = repository)

    @Provides
    fun provideSetHintVisibilityUseCase(
        repository: Repository
    ): SetHintVisibilityUseCase = SetHintVisibilityUseCase(repository = repository)

    @Provides
    fun provideSetPasswordUseCase(
        repository: Repository,
        tinkRepository: TinkRepository
    ): SetPasswordUseCase =
        SetPasswordUseCase(repository = repository, tinkRepository = tinkRepository)

    @Provides
    fun provideSetSkinCalculatorUseCase(
        repository: Repository,
        tinkRepository: TinkRepository
    ): SetSkinUseCase = SetSkinUseCase(repository = repository, tinkRepository = tinkRepository)

    @Provides
    fun provideSetSkinDefaultUseCase(
        repository: Repository
    ): SetSkinDefaultUseCase = SetSkinDefaultUseCase(repository = repository)

    @Provides
    fun provideVerifyCalculatorCombinationUseCase(
        repository: Repository,
        tinkRepository: TinkRepository
    ): VerifySkinUseCase =
        VerifySkinUseCase(repository = repository, tinkRepository = tinkRepository)

    @Provides
    fun provideVerifyPasswordUseCase(
        repository: Repository,
        tinkRepository: TinkRepository
    ): VerifyAuthUseCase =
        VerifyAuthUseCase(repository = repository, tinkRepository = tinkRepository)

    @Provides
    fun provideSetBindTinkAdUseCase(
        repository: Repository,
        tinkRepository: TinkRepository
    ): SetBindTinkAdUseCase =
        SetBindTinkAdUseCase(repository = repository, tinkRepository = tinkRepository)

}