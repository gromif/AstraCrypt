package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.usecase.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.GetItemDetailsUseCase
import io.gromif.astracrypt.files.domain.usecase.GetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.GetRecentItemsUseCase
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUsecase
import io.gromif.astracrypt.files.domain.usecase.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.SetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.SetDatabaseAeadUseCase
import io.gromif.astracrypt.files.domain.usecase.SetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.SetStateUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideGetValidationRulesUsecase() = GetValidationRulesUsecase()

    @Provides
    fun provideCreateFolderUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = CreateFolderUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideDeleteUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = DeleteUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideGetListViewModeUseCase(settingsRepository: SettingsRepository) =
        GetListViewModeUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideSetListViewModeUseCase(settingsRepository: SettingsRepository) =
        SetListViewModeUseCase(settingsRepository = settingsRepository)

    @Provides
    fun provideMoveUseCase(repository: Repository) = MoveUseCase(repository)

    @Provides
    fun provideRenameUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = RenameUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideSetStarredUseCase(repository: Repository) = SetStateUseCase(repository = repository)

    @Provides
    fun provideGetRecentItemsUseCase(
        getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
        repository: Repository,
    ) = GetRecentItemsUseCase(
        getAeadInfoFlowUseCase = getAeadInfoFlowUseCase,
        repository = repository,
    )

    @Provides
    fun provideGetItemDetailsUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = GetItemDetailsUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideSetDatabaseAeadUseCase(
        setAeadInfoUseCase: SetAeadInfoUseCase,
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = SetDatabaseAeadUseCase(
        setAeadInfoUseCase = setAeadInfoUseCase,
        getAeadInfoUseCase = getAeadInfoUseCase,
        repository = repository
    )


    @Provides
    fun provideGetAeadInfoFlowUseCase(settingsRepository: SettingsRepository) =
        GetAeadInfoFlowUseCase(settingsRepository)

    @Provides
    fun provideGetAeadInfoUseCase(settingsRepository: SettingsRepository) =
        GetAeadInfoUseCase(settingsRepository)

    @Provides
    fun provideSetAeadInfoUseCase(settingsRepository: SettingsRepository) =
        SetAeadInfoUseCase(settingsRepository)

}