package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.files.domain.usecase.export.ExternalExportUseCase
import io.gromif.astracrypt.files.domain.usecase.export.InternalExportUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object ExportUseCaseModule {

    @Provides
    fun provideExternalExportUseCase(itemExporter: ItemExporter) =
        ExternalExportUseCase(itemExporter = itemExporter)

    @Provides
    fun provideInternalExportUseCase(itemExporter: ItemExporter) =
        InternalExportUseCase(itemExporter = itemExporter)
}
