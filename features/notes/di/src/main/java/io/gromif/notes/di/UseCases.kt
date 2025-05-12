package io.gromif.notes.di

import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.paging.PagingProvider
import io.gromif.notes.domain.repository.Repository
import io.gromif.notes.domain.repository.SettingsRepository
import io.gromif.notes.domain.usecase.CreateUseCase
import io.gromif.notes.domain.usecase.DeleteByIdUseCase
import io.gromif.notes.domain.usecase.GetAeadPreferenceFlowUseCase
import io.gromif.notes.domain.usecase.GetAeadPreferenceUseCase
import io.gromif.notes.domain.usecase.GetNotesListFlow
import io.gromif.notes.domain.usecase.LoadByIdUseCase
import io.gromif.notes.domain.usecase.SetAeadPreferenceUseCase
import io.gromif.notes.domain.usecase.UpdateByIdUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UseCases {

    @Provides
    fun provideGetPagingUseCase(
        pagingProvider: PagingProvider<PagingData<Note>>,
    ): GetNotesListFlow<PagingData<Note>> = GetNotesListFlow(
        pagingProvider = pagingProvider,
    )

    @Provides
    fun provideCreateUseCase(
        getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
        repositoryImpl: Repository,
    ): CreateUseCase = CreateUseCase(
        getAeadPreferenceUseCase = getAeadPreferenceUseCase,
        repository = repositoryImpl,
    )

    @Provides
    fun provideSetAeadPreferenceUseCase(
        settingsRepository: SettingsRepository,
    ): SetAeadPreferenceUseCase = SetAeadPreferenceUseCase(
        settingsRepository = settingsRepository,
    )

    @Provides
    fun provideGetAeadPreferenceFlowUseCase(
        settingsRepository: SettingsRepository,
    ): GetAeadPreferenceFlowUseCase = GetAeadPreferenceFlowUseCase(
        settingsRepository = settingsRepository,
    )

    @Provides
    fun provideGetAeadPreferenceUseCase(
        settingsRepository: SettingsRepository,
    ): GetAeadPreferenceUseCase = GetAeadPreferenceUseCase(
        settingsRepository = settingsRepository,
    )

    @Provides
    fun provideLoadByIdUseCase(
        getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
        repositoryImpl: Repository,
    ): LoadByIdUseCase = LoadByIdUseCase(
        getAeadPreferenceUseCase = getAeadPreferenceUseCase,
        repository = repositoryImpl,
    )

    @Provides
    fun provideUpdateByIdUseCase(
        getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
        repositoryImpl: Repository,
    ): UpdateByIdUseCase = UpdateByIdUseCase(
        getAeadPreferenceUseCase = getAeadPreferenceUseCase,
        repository = repositoryImpl,
    )

    @Provides
    fun provideDeleteByIdUseCase(
        repositoryImpl: Repository
    ): DeleteByIdUseCase = DeleteByIdUseCase(
        repository = repositoryImpl
    )

}