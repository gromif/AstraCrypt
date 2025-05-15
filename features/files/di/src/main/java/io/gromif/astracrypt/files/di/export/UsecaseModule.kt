package io.gromif.astracrypt.files.di.export

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.files.domain.usecase.ExportUseCase
import io.gromif.astracrypt.files.domain.usecase.PrivateExportUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideExportUseCase(itemExporter: ItemExporter) =
        ExportUseCase(itemExporter = itemExporter)

    @Provides
    fun providePrivateExportUseCase(itemExporter: ItemExporter) =
        PrivateExportUseCase(itemExporter = itemExporter)

}