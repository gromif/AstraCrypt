package com.nevidimka655.astracrypt.di

import com.nevidimka655.astracrypt.features.auth.AuthManager
import com.nevidimka655.astracrypt.utils.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun provideAuthManager(
        defaultDataStoreManager: DefaultDataStoreManager,
        settingsDataStoreManager: SettingsDataStoreManager
    ) = AuthManager(
        defaultDataStoreManager = defaultDataStoreManager,
        settingsDataStoreManager = settingsDataStoreManager
    )

}