package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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

    @Provides
    fun provideCreateFolderUseCase(repository: Repository): CreateFolderUseCase =
        CreateFolderUseCase(repository = repository)

    @Provides
    fun provideDeleteUseCase(repository: Repository): DeleteUseCase =
        DeleteUseCase(repository = repository)

    @Provides
    fun provideGetListViewModeUseCase(settingsRepository: SettingsRepository): GetListViewModeUseCase =
        GetListViewModeUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideMoveUseCase(repository: Repository): MoveUseCase =
        MoveUseCase(repository = repository)

    @Provides
    fun provideRenameUseCase(repository: Repository): RenameUseCase =
        RenameUseCase(repository = repository)

    @Provides
    fun provideSetStarredUseCase(repository: Repository): SetStarredUseCase =
        SetStarredUseCase(repository = repository)

    @Provides
    fun provideGetRecentItemsUseCase(repository: Repository): GetRecentItemsUseCase =
        GetRecentItemsUseCase(repository = repository)

    @Provides
    fun provideGetItemDetailsUseCase(repository: Repository): GetItemDetailsUseCase =
        GetItemDetailsUseCase(repository = repository)

}