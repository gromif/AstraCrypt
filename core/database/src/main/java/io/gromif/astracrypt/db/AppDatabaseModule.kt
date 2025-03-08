package io.gromif.astracrypt.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "storage"

@Module
@InstallIn(SingletonComponent::class)
internal object AppDatabaseModule {

    @Singleton
    @Provides
    fun provideFilesDao(database: AppDatabase) = database.getFilesDao()

    @Singleton
    @Provides
    fun provideNotesDao(database: AppDatabase) = database.getNotesDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

}