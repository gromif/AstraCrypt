package io.gromif.lab_zip.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.lab_zip.domain.Repository
import io.gromif.lab_zip.domain.usecase.GetFileInfosUseCase
import io.gromif.lab_zip.domain.usecase.GetSourceFileInfoUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideGetSourceFileInfoUseCase(
        repository: Repository
    ): GetSourceFileInfoUseCase = GetSourceFileInfoUseCase(repository = repository)

    @Provides
    fun provideGetFileInfosUseCase(
        repository: Repository
    ): GetFileInfosUseCase = GetFileInfosUseCase(repository = repository)

}