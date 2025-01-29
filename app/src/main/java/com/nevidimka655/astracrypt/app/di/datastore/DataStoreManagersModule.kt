package com.nevidimka655.astracrypt.app.di.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
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
    fun provideKeysetDataStoreManager(
        @KeysetDataStore dataStore: DataStore<Preferences>
    ) = KeysetDataStoreManager(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideAppearanceManager(
        @DefaultDataStore dataStore: DataStore<Preferences>
    ) = AppearanceManager(dataStore = dataStore)

}