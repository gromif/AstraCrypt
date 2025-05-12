package io.gromif.notes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.notes.domain.repository.Repository
import io.gromif.notes.domain.usecase.GetAeadPreferenceUseCase
import io.gromif.notes.domain.usecase.SetAeadPreferenceUseCase
import io.gromif.notes.domain.usecase.UpdateNotesAeadUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object WorkerUseCases {

    @Provides
    fun provideUpdateNotesAeadUseCase(
        getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
        setAeadPreferenceUseCase: SetAeadPreferenceUseCase,
        repositoryImpl: Repository
    ): UpdateNotesAeadUseCase = UpdateNotesAeadUseCase(
        getAeadPreferenceUseCase = getAeadPreferenceUseCase,
        setAeadPreferenceUseCase = setAeadPreferenceUseCase,
        repository = repositoryImpl
    )

}