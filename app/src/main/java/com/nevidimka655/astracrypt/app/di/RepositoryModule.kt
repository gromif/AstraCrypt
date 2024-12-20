package com.nevidimka655.astracrypt.app.di

import android.content.Context
import androidx.room.Room
import com.nevidimka655.astracrypt.data.crypto.AeadManager
import com.nevidimka655.astracrypt.data.database.AppDatabase
import com.nevidimka655.astracrypt.data.database.RepositoryEncryption
import com.nevidimka655.astracrypt.data.database.daos.NotesDao
import com.nevidimka655.astracrypt.data.database.daos.StorageItemDao
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryImpl
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryProvider
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryImpl
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryProvider
import com.nevidimka655.astracrypt.domain.repository.files.FilesRepository
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "storage"

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFilesRepository(
        storage: StorageItemDao
    ): FilesRepository = FilesRepositoryImpl(dao = storage)

    @Singleton
    @Provides
    fun provideFilesRepositoryProvider(
        plainFilesRepository: FilesRepository,
        settingsDataStoreManager: SettingsDataStoreManager
    ): FilesRepositoryProvider = FilesRepositoryProvider(
        plainFilesRepository = plainFilesRepository,
        aeadInfoFlow = settingsDataStoreManager.aeadInfoFlow
    )

    @Singleton
    @Provides
    fun provideFilesAeadRepository(
        keysetManager: KeysetManager, aeadManager: AeadManager
    ) = RepositoryEncryption(keysetManager = keysetManager, aeadManager = aeadManager)

    @Singleton
    @Provides
    fun provideStorageDao(database: AppDatabase) = database.getStorageItemDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

}