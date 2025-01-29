package io.gromif.astracrypt.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.astracrypt.profile.domain.usecase.GetProfileUsecase
import io.gromif.astracrypt.profile.domain.usecase.GetValidationRulesUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetAvatarUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetExternalAvatarUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetNameUsecase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetProfileUsecase(settingsRepository: SettingsRepository) =
        GetProfileUsecase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetNameUsecase(settingsRepository: SettingsRepository) =
        SetNameUsecase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAvatarUsecase(settingsRepository: SettingsRepository): SetAvatarUsecase =
        SetAvatarUsecase(settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetExternalAvatarUsecase(
        repository: Repository,
        settingsRepository: SettingsRepository,
    ): SetExternalAvatarUsecase = SetExternalAvatarUsecase(repository, settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetValidationRulesUsecase(): GetValidationRulesUsecase = GetValidationRulesUsecase()

}