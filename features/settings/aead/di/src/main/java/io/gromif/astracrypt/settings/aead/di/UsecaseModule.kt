package io.gromif.astracrypt.settings.aead.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository
import io.gromif.astracrypt.settings.aead.domain.usecase.GetSettingsAeadUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.SetSettingsAeadUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideSetSettingsAeadUseCase(
        settingsRepository: SettingsRepository
    ): SetSettingsAeadUseCase = SetSettingsAeadUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideGetSettingsAeadUseCase(
        settingsRepository: SettingsRepository
    ): GetSettingsAeadUseCase = GetSettingsAeadUseCase(settingsRepository = settingsRepository)

}