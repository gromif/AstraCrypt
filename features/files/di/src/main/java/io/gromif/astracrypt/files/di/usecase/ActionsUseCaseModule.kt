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
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object ActionsUseCaseModule {

    @Provides
    fun provideCreateFolderUseCase(
        getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = CreateFolderUseCase(
        getCurrentNavFolderUseCase = getCurrentNavFolderUseCase,
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
        getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
        repository: Repository
    ) = MoveUseCase(getCurrentNavFolderUseCase, repository)

    @Provides
    fun provideRenameUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = RenameUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideSetStarredUseCase(repository: Repository) = SetStateUseCase(repository = repository)

}