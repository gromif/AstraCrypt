package com.nevidimka655.astracrypt.app.di.notes

import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryImpl
import com.nevidimka655.astracrypt.domain.usecase.notes.CreateNewNoteUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.LoadNoteByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NotesModule {

    @Provides
    fun provideCreateNewNoteUseCase(
        notesRepositoryImpl: NotesRepositoryImpl
    ): CreateNewNoteUseCase = CreateNewNoteUseCase(
        repository = notesRepositoryImpl
    )

    @Provides
    fun provideLoadNoteByIdUseCase(
        notesRepositoryImpl: NotesRepositoryImpl
    ): LoadNoteByIdUseCase = LoadNoteByIdUseCase(
        repository = notesRepositoryImpl
    )

}