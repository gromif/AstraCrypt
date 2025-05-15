package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.usecase.preferences.GetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.preferences.SetListViewModeUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesUseCaseModule {

    @Provides
    fun provideGetListViewModeUseCase(settingsRepository: SettingsRepository) =
        GetListViewModeUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideSetListViewModeUseCase(settingsRepository: SettingsRepository) =
        SetListViewModeUseCase(settingsRepository = settingsRepository)

}