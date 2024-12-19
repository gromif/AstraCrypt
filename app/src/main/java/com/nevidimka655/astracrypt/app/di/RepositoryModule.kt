package com.nevidimka655.astracrypt.app.di

import android.content.Context
import androidx.room.Room
import com.nevidimka655.astracrypt.app.utils.AeadManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.repository.files.FilesFilesRepositoryImpl
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryProvider
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryImpl
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryProvider
import com.nevidimka655.astracrypt.data.room.AppDatabase
import com.nevidimka655.astracrypt.data.room.RepositoryEncryption
import com.nevidimka655.astracrypt.domain.repository.files.FilesRepository
import com.nevidimka655.astracrypt.domain.room.daos.NotesDao
import com.nevidimka655.astracrypt.domain.room.daos.StorageItemDao
import com.nevidimka655.crypto.tink.KeysetManager
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
    ): FilesRepository = FilesFilesRepositoryImpl(dao = storage)

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

    // Notes

    @Singleton
    @Provides
    fun provideNotesRepositoryProvider(
        notesRepositoryImpl: NotesRepositoryImpl,
        settingsDataStoreManager: SettingsDataStoreManager
    ): NotesRepositoryProvider = NotesRepositoryProvider(
        notesRepositoryImpl = notesRepositoryImpl,
        aeadInfoFlow = settingsDataStoreManager.aeadInfoFlow
    )

    @Singleton
    @Provides
    fun provideNotesRepository(notes: NotesDao): NotesRepositoryImpl =
        NotesRepositoryImpl(dao = notes)

    @Singleton
    @Provides
    fun provideNotesDao(database: AppDatabase) = database.getNotesDao()

}