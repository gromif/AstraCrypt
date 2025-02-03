package io.gromif.astracrypt.files.di.import_di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.usecase.ImportUseCase
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil

@Module
@InstallIn(SingletonComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideImportUseCase(
        repository: Repository,
        settingsRepository: SettingsRepository,
        fileUtilFactory: FileUtil.Factory,
        previewUtil: PreviewUtil,
        flagsUtil: FlagsUtil
    ): ImportUseCase = ImportUseCase(
        repository = repository,
        settingsRepository = settingsRepository,
        fileUtilFactory = fileUtilFactory,
        previewUtil = previewUtil,
        flagsUtil = flagsUtil
    )

}