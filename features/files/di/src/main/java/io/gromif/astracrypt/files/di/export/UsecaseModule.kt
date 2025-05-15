package io.gromif.astracrypt.files.di.export

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.files.domain.usecase.ExternalExportUseCase
import io.gromif.astracrypt.files.domain.usecase.InternalExportUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideExternalExportUseCase(itemExporter: ItemExporter) =
        ExternalExportUseCase(itemExporter = itemExporter)

    @Provides
    fun provideInternalExportUseCase(itemExporter: ItemExporter) =
        InternalExportUseCase(itemExporter = itemExporter)

}