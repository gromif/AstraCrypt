package io.gromif.notes.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.notes.data.repository.SettingsRepositoryImpl
import io.gromif.notes.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsRepositoryModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(
        @NotesDataStore dataStore: DataStore<Preferences>
    ): SettingsRepository = SettingsRepositoryImpl(dataStore = dataStore)

}