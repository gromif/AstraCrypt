package com.nevidimka655.astracrypt.app.di.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.data.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.crypto.tink.core.encoders.Base64Service
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.KeysetManager
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
    fun provideKeysetDataStoreManager(
        @KeysetDataStore dataStore: DataStore<Preferences>
    ) = KeysetDataStoreManager(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideAppearanceManager(
        @DefaultDataStore dataStore: DataStore<Preferences>
    ) = AppearanceManager(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideSettingsDataStoreManager(
        @SettingsDataStore dataStore: DataStore<Preferences>,
        keysetManager: KeysetManager,
        sha256Service: Sha256Service,
        base64Service: Base64Service
    ) = SettingsDataStoreManager(
        dataStore = dataStore,
        keysetManager = keysetManager,
        sha256Service = sha256Service,
        base64Service = base64Service
    )

}