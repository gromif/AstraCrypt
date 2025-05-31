package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.service.ClockService
import io.gromif.astracrypt.files.domain.usecase.actions.ImportUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil

@Module
@InstallIn(SingletonComponent::class)
internal object ImportUseCaseModule {

    @Provides
    fun provideImportUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        clockService: ClockService,
        itemWriter: ItemWriter,
        fileUtilFactory: FileUtil.Factory,
        previewUtil: PreviewUtil,
        flagsUtil: FlagsUtil
    ) = ImportUseCase(
        getAeadInfoUseCase = getAeadInfoUseCase,
        clockService = clockService,
        itemWriter = itemWriter,
        fileUtilFactory = fileUtilFactory,
        previewUtil = previewUtil,
        flagsUtil = flagsUtil
    )
}
