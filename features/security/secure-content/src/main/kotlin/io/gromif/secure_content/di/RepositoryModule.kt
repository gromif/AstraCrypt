package io.gromif.secure_content.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.secure_content.data.SettingsRepositoryImpl
import io.gromif.secure_content.domain.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun getSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository =
        SettingsRepositoryImpl(dataStore = dataStore)

}