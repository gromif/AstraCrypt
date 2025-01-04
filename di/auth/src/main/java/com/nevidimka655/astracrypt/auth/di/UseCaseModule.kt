package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.usecase.DisableAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintTextUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetPasswordUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinCalculatorUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinDefaultUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyCalculatorCombinationUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyPasswordUseCase
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
        repository: Repository
    ): SetPasswordUseCase = SetPasswordUseCase(repository = repository)

    @Provides
    fun provideSetSkinCalculatorUseCase(
        repository: Repository
    ): SetSkinCalculatorUseCase = SetSkinCalculatorUseCase(repository = repository)

    @Provides
    fun provideSetSkinDefaultUseCase(
        repository: Repository
    ): SetSkinDefaultUseCase = SetSkinDefaultUseCase(repository = repository)

    @Provides
    fun provideVerifyCalculatorCombinationUseCase(
        repository: Repository
    ): VerifyCalculatorCombinationUseCase = VerifyCalculatorCombinationUseCase(repository = repository)

    @Provides
    fun provideVerifyPasswordUseCase(
        repository: Repository
    ): VerifyPasswordUseCase = VerifyPasswordUseCase(repository = repository)

    @Provides
    fun provideSetBindTinkAdUseCase(
        repository: Repository
    ): SetBindTinkAdUseCase = SetBindTinkAdUseCase(repository = repository)

}