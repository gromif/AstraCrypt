package com.nevidimka655.astracrypt.di

import com.nevidimka655.astracrypt.features.auth.AuthManager
import com.nevidimka655.astracrypt.utils.datastore.DefaultDataStoreManager
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
        defaultDataStoreManager: DefaultDataStoreManager
    ) = AuthManager(
        defaultDataStoreManager = defaultDataStoreManager
    )

}