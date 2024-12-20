package com.nevidimka655.astracrypt.app.di

import com.nevidimka655.astracrypt.data.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.auth.AuthManager
import com.nevidimka655.crypto.tink.core.hash.Sha384Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthManager(
        defaultDataStoreManager: DefaultDataStoreManager,
        settingsDataStoreManager: SettingsDataStoreManager,
        sha384Service: Sha384Service
    ) = AuthManager(
        defaultDataStoreManager = defaultDataStoreManager,
        settingsDataStoreManager = settingsDataStoreManager,
        sha384Service = sha384Service
    )

}