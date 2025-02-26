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
    fun provideGetAeadInfoFlowUseCase(
        settingsRepository: SettingsRepository,
    ): GetAeadInfoFlowUseCase = GetAeadInfoFlowUseCase(settingsRepository)

    @Provides
    fun provideGetAeadInfoUseCase(
        settingsRepository: SettingsRepository,
    ): GetAeadInfoUseCase = GetAeadInfoUseCase(settingsRepository)

    @Provides
    fun provideSetAeadInfoUseCase(
        settingsRepository: SettingsRepository,
    ): SetAeadInfoUseCase = SetAeadInfoUseCase(settingsRepository)

}