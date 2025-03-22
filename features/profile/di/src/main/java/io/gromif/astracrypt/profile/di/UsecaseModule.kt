package io.gromif.astracrypt.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.astracrypt.profile.domain.usecase.GetAeadModeFlowUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetAvatarAeadUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetProfileFlowUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetProfileUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetAeadModeUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetAvatarUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetExternalAvatarUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetNameUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetProfileUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetProfileUsecase(settingsRepository: SettingsRepository) =
        GetProfileUseCase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetProfileUsecase(settingsRepository: SettingsRepository) =
        SetProfileUseCase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetProfileFlowUsecase(settingsRepository: SettingsRepository) =
        GetProfileFlowUseCase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetAvatarAeadUseCase(settingsRepository: SettingsRepository) =
        GetAvatarAeadUseCase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetNameUsecase(
        setProfileUsecase: SetProfileUseCase,
        getProfileUsecase: GetProfileUseCase,
    ) = SetNameUseCase(setProfileUsecase, getProfileUsecase)

    @ViewModelScoped
    @Provides
    fun provideSetAvatarUsecase(
        setProfileUsecase: SetProfileUseCase,
        getProfileUsecase: GetProfileUseCase,
    ) = SetAvatarUseCase(setProfileUsecase, getProfileUsecase)

    @ViewModelScoped
    @Provides
    fun provideSetExternalAvatarUsecase(
        setProfileUsecase: SetProfileUseCase,
        getProfileUsecase: GetProfileUseCase,
        getAvatarAeadUseCase: GetAvatarAeadUseCase,
        repository: Repository,
    ) = SetExternalAvatarUseCase(setProfileUsecase, getProfileUsecase, getAvatarAeadUseCase, repository)

    @ViewModelScoped
    @Provides
    fun provideGetValidationRulesUsecase(): GetValidationRulesUseCase = GetValidationRulesUseCase()

    @ViewModelScoped
    @Provides
    fun provideGetAeadModeFlowUseCase(
        settingsRepository: SettingsRepository
    ) = GetAeadModeFlowUseCase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAeadModeUseCase(
        settingsRepository: SettingsRepository
    ) = SetAeadModeUseCase(settingsRepository)

}