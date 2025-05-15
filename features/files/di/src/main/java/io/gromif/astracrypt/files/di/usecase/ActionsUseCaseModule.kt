package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.actions.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.ImportUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.SetStateUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil

@Module
@InstallIn(SingletonComponent::class)
internal object ActionsUseCaseModule {

    @Provides
    fun provideImportUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
        fileUtilFactory: FileUtil.Factory,
        previewUtil: PreviewUtil,
        flagsUtil: FlagsUtil
    ) = ImportUseCase(
        getAeadInfoUseCase = getAeadInfoUseCase,
        repository = repository,
        fileUtilFactory = fileUtilFactory,
        previewUtil = previewUtil,
        flagsUtil = flagsUtil
    )

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
    fun provideMoveUseCase(repository: Repository) = MoveUseCase(repository)

    @Provides
    fun provideRenameUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = RenameUseCase(getAeadInfoUseCase, repository)

    @Provides
    fun provideSetStarredUseCase(repository: Repository) = SetStateUseCase(repository = repository)

}