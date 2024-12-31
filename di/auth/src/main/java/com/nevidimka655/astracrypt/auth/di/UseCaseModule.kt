package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.usecase.DisableAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
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
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    @Singleton
    @Provides
    fun provideDisableAuthUseCase(
        repository: Repository
    ): DisableAuthUseCase = DisableAuthUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideGetAuthFlowUseCase(
        repository: Repository
    ): GetAuthFlowUseCase = GetAuthFlowUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideSetHintTextUseCase(
        repository: Repository
    ): SetHintTextUseCase = SetHintTextUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideSetHintVisibilityUseCase(
        repository: Repository
    ): SetHintVisibilityUseCase = SetHintVisibilityUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideSetPasswordUseCase(
        repository: Repository
    ): SetPasswordUseCase = SetPasswordUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideSetSkinCalculatorUseCase(
        repository: Repository
    ): SetSkinCalculatorUseCase = SetSkinCalculatorUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideSetSkinDefaultUseCase(
        repository: Repository
    ): SetSkinDefaultUseCase = SetSkinDefaultUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideVerifyCalculatorCombinationUseCase(
        repository: Repository
    ): VerifyCalculatorCombinationUseCase = VerifyCalculatorCombinationUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideVerifyPasswordUseCase(
        repository: Repository
    ): VerifyPasswordUseCase = VerifyPasswordUseCase(repository = repository)

}