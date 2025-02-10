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
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUsecase
import io.gromif.astracrypt.files.domain.usecase.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.SetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.SetStateUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetValidationRulesUsecase(): GetValidationRulesUsecase = GetValidationRulesUsecase()

    @Provides
    fun provideCreateFolderUseCase(
        repository: Repository,
        settingsRepository: SettingsRepository,
    ): CreateFolderUseCase = CreateFolderUseCase(repository, settingsRepository)

    @Provides
    fun provideDeleteUseCase(
        repository: Repository,
        settingsRepository: SettingsRepository,
    ): DeleteUseCase =
        DeleteUseCase(repository, settingsRepository)

    @Provides
    fun provideGetListViewModeUseCase(settingsRepository: SettingsRepository): GetListViewModeUseCase =
        GetListViewModeUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideSetListViewModeUseCase(settingsRepository: SettingsRepository): SetListViewModeUseCase =
        SetListViewModeUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideMoveUseCase(repository: Repository): MoveUseCase = MoveUseCase(repository)

    @Provides
    fun provideRenameUseCase(
        repository: Repository,
        settingsRepository: SettingsRepository,
    ): RenameUseCase = RenameUseCase(repository, settingsRepository)

    @Provides
    fun provideSetStarredUseCase(repository: Repository): SetStateUseCase =
        SetStateUseCase(repository = repository)

    @Provides
    fun provideGetRecentItemsUseCase(repository: Repository): GetRecentItemsUseCase =
        GetRecentItemsUseCase(repository = repository)

    @Provides
    fun provideGetItemDetailsUseCase(
        repository: Repository,
        settingsRepository: SettingsRepository,
    ): GetItemDetailsUseCase = GetItemDetailsUseCase(repository, settingsRepository)

}