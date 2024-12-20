package com.nevidimka655.astracrypt.app.di.notes

import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryImpl
import com.nevidimka655.astracrypt.domain.usecase.notes.CreateNewNoteUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.DeleteByIdUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.LoadNoteByIdUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.UpdateNoteByIdUseCase
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

    @Provides
    fun provideUpdateNoteByIdUseCase(
        notesRepositoryImpl: NotesRepositoryImpl
    ): UpdateNoteByIdUseCase = UpdateNoteByIdUseCase(
        repository = notesRepositoryImpl
    )

    @Provides
    fun provideDeleteByIdUseCase(
        notesRepositoryImpl: NotesRepositoryImpl
    ): DeleteByIdUseCase = DeleteByIdUseCase(
        repository = notesRepositoryImpl
    )

}