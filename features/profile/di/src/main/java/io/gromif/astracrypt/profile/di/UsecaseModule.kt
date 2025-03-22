package io.gromif.astracrypt.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.astracrypt.profile.domain.usecase.GetAeadModeFlowUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetProfileFlowUsecase
import io.gromif.astracrypt.profile.domain.usecase.GetProfileUsecase
import io.gromif.astracrypt.profile.domain.usecase.GetValidationRulesUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetAeadModeUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetAvatarUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetExternalAvatarUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetNameUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetProfileUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetProfileUsecase(settingsRepository: SettingsRepository) =
        GetProfileUsecase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetProfileUsecase(settingsRepository: SettingsRepository) =
        SetProfileUseCase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetProfileFlowUsecase(settingsRepository: SettingsRepository) =
        GetProfileFlowUsecase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetNameUsecase(
        setProfileUsecase: SetProfileUseCase,
        getProfileUsecase: GetProfileUsecase,
    ) = SetNameUsecase(setProfileUsecase, getProfileUsecase)

    @ViewModelScoped
    @Provides
    fun provideSetAvatarUsecase(
        setProfileUsecase: SetProfileUseCase,
        getProfileUsecase: GetProfileUsecase,
    ) = SetAvatarUsecase(setProfileUsecase, getProfileUsecase)

    @ViewModelScoped
    @Provides
    fun provideSetExternalAvatarUsecase(
        setProfileUsecase: SetProfileUseCase,
        getProfileUsecase: GetProfileUsecase,
        repository: Repository,
        settingsRepository: SettingsRepository,
    ) = SetExternalAvatarUsecase(setProfileUsecase, getProfileUsecase, repository, settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetValidationRulesUsecase(): GetValidationRulesUsecase = GetValidationRulesUsecase()

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