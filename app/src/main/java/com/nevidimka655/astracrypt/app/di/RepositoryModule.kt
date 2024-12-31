package com.nevidimka655.astracrypt.app.di

import com.nevidimka655.astracrypt.data.crypto.AeadManager
import com.nevidimka655.astracrypt.data.database.RepositoryEncryption
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.files.db.FilesDao
import com.nevidimka655.astracrypt.data.repository.RepositoryImpl
import com.nevidimka655.astracrypt.data.repository.RepositoryProviderImpl
import com.nevidimka655.astracrypt.domain.repository.Repository
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        storage: FilesDao
    ): Repository = RepositoryImpl(dao = storage)

    @Singleton
    @Provides
    fun provideRepositoryProvider(
        plainRepository: Repository,
        settingsDataStoreManager: SettingsDataStoreManager
    ): RepositoryProviderImpl = RepositoryProviderImpl(
        plainRepository = plainRepository,
        aeadInfoFlow = settingsDataStoreManager.aeadInfoFlow
    )

    @Singleton
    @Provides
    fun provideAeadRepository(
        keysetManager: KeysetManager, aeadManager: AeadManager
    ) = RepositoryEncryption(keysetManager = keysetManager, aeadManager = aeadManager)

}