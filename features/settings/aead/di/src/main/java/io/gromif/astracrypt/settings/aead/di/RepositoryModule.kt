package io.gromif.astracrypt.settings.aead.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.settings.aead.data.repository.RepositoryImpl
import io.gromif.astracrypt.settings.aead.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.settings.aead.domain.repository.Repository
import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        settingsRepository: SettingsRepository,
    ): Repository = RepositoryImpl(
        settingsRepository = settingsRepository,
    )

    @Provides
    @ViewModelScoped
    fun provideSettingsRepository(
        @AeadDataStore dataStore: DataStore<Preferences>
    ): SettingsRepository = SettingsRepositoryImpl(dataStore = dataStore)

}