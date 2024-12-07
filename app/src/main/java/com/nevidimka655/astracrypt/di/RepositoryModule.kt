package com.nevidimka655.astracrypt.di

import android.content.Context
import androidx.room.Room
import com.nevidimka655.astracrypt.room.AppDatabase
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.RepositoryEncryption
import com.nevidimka655.astracrypt.room.daos.NotesDao
import com.nevidimka655.astracrypt.room.daos.StorageItemDao
import com.nevidimka655.astracrypt.utils.AeadManager
import com.nevidimka655.crypto.tink.KeysetFactory
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
    fun provideRepository(
        repositoryEncryption: RepositoryEncryption,
        aeadManager: AeadManager,
        storage: StorageItemDao,
        notes: NotesDao
    ) = Repository(
        repositoryEncryption = repositoryEncryption,
        aeadManager = aeadManager,
        storage = storage,
        notes = notes
    )

    @Singleton
    @Provides
    fun provideRepositoryEncryption(
        keysetFactory: KeysetFactory, aeadManager: AeadManager
    ) = RepositoryEncryption(keysetFactory = keysetFactory, aeadManager = aeadManager)

    @Singleton
    @Provides
    fun provideStorageDao(database: AppDatabase) = database.getStorageItemDao()

    @Singleton
    @Provides
    fun provideNotesDao(database: AppDatabase) = database.getNotesDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

}