package com.nevidimka655.astracrypt.di.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nevidimka655.astracrypt.utils.datastore.AppearanceManager
import com.nevidimka655.astracrypt.utils.datastore.DefaultDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreManagersModule {

    @Singleton
    @Provides
    fun provideDefaultDataStoreManager(
        @DefaultDataStore dataStore: DataStore<Preferences>
    ) = DefaultDataStoreManager(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideAppearanceManager(
        @DefaultDataStore dataStore: DataStore<Preferences>
    ) = AppearanceManager(dataStore = dataStore)

}