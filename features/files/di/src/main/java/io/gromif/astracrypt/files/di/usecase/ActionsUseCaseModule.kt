package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.actions.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.SetStateUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object ActionsUseCaseModule {

    @Provides
    fun provideCreateFolderUseCase(
        getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = CreateFolderUseCase(
        getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCase,
        getAeadInfoUseCase = getAeadInfoUseCase,
        repository = repository
    )

    @Provides
    fun provideDeleteUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = DeleteUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideMoveUseCase(
        getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
        repository: Repository
    ) = MoveUseCase(
        getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCase,
        repository = repository
    )

    @Provides
    fun provideRenameUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = RenameUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideSetStarredUseCase(repository: Repository) = SetStateUseCase(repository = repository)

}