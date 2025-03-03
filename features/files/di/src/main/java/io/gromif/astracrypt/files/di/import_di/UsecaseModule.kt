package io.gromif.astracrypt.files.di.import_di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.ImportUseCase
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil

@Module
@InstallIn(SingletonComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideImportUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
        fileUtilFactory: FileUtil.Factory,
        previewUtil: PreviewUtil,
        flagsUtil: FlagsUtil
    ): ImportUseCase = ImportUseCase(
        getAeadInfoUseCase = getAeadInfoUseCase,
        repository = repository,
        fileUtilFactory = fileUtilFactory,
        previewUtil = previewUtil,
        flagsUtil = flagsUtil
    )

}