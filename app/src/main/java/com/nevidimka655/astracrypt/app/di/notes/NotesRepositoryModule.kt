package com.nevidimka655.astracrypt.app.di.notes

import com.nevidimka655.astracrypt.data.database.AppDatabase
import com.nevidimka655.astracrypt.data.database.daos.NotesDao
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryImpl
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NotesRepositoryModule {

    @Provides
    fun provideNotesRepositoryProvider(
        notesRepositoryImpl: NotesRepositoryImpl,
        settingsDataStoreManager: SettingsDataStoreManager
    ): NotesRepositoryProvider = NotesRepositoryProvider(
        notesRepositoryImpl = notesRepositoryImpl,
        aeadInfoFlow = settingsDataStoreManager.aeadInfoFlow
    )

    @Provides
    fun provideNotesRepository(notes: NotesDao): NotesRepositoryImpl =
        NotesRepositoryImpl(dao = notes)

    @Provides
    fun provideNotesDao(database: AppDatabase) = database.getNotesDao()

}