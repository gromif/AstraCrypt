package io.gromif.astracrypt.di.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.utils.AppearanceManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreManagersModule {

    @Singleton
    @Provides
    fun provideAppearanceManager(
        dataStore: DataStore<Preferences>
    ) = AppearanceManager(dataStore = dataStore)

}