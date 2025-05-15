package io.gromif.astracrypt.files.di.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.files.di.FilesDataStore
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsRepositoryModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(
        @FilesDataStore dataStore: DataStore<Preferences>
    ): SettingsRepository = SettingsRepositoryImpl(dataStore = dataStore)

}