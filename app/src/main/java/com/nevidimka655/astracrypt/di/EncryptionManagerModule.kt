package com.nevidimka655.astracrypt.di

import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import com.nevidimka655.crypto.tink.KeysetFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptionManagerModule {

    @Singleton
    @Provides
    fun provideEncryptionManager(
        settingsDataStoreManager: SettingsDataStoreManager,
        keysetFactory: KeysetFactory
    ) = EncryptionManager(
        settingsDataStoreManager = settingsDataStoreManager
    )

}