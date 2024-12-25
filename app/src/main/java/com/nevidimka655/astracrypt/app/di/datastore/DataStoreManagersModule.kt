package com.nevidimka655.astracrypt.app.di.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.data.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.core.hash.Sha256Util
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
        sha256Util: Sha256Util,
        base64Util: Base64Util
    ) = SettingsDataStoreManager(
        dataStore = dataStore,
        keysetManager = keysetManager,
        sha256Util = sha256Util,
        base64Util = base64Util
    )

}