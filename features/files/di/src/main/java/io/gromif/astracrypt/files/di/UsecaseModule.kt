package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.usecase.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.GetItemDetailsUseCase
import io.gromif.astracrypt.files.domain.usecase.GetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.GetRecentItemsUseCase
import io.gromif.astracrypt.files.domain.usecase.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.SetStarredUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @ViewModelScoped
    @Provides
    fun provideCreateFolderUseCase(repository: Repository): CreateFolderUseCase =
        CreateFolderUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideDeleteUseCase(repository: Repository): DeleteUseCase =
        DeleteUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetListViewModeUseCase(settingsRepository: SettingsRepository): GetListViewModeUseCase =
        GetListViewModeUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideMoveUseCase(repository: Repository): MoveUseCase =
        MoveUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideRenameUseCase(repository: Repository): RenameUseCase =
        RenameUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideSetStarredUseCase(repository: Repository): SetStarredUseCase =
        SetStarredUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetRecentItemsUseCase(repository: Repository): GetRecentItemsUseCase =
        GetRecentItemsUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetItemDetailsUseCase(repository: Repository): GetItemDetailsUseCase =
        GetItemDetailsUseCase(repository = repository)

}