package com.nevidimka655.di.lab_zip

import com.nevidimka655.domain.lab_zip.Repository
import com.nevidimka655.domain.lab_zip.usecase.GetFileInfosUseCase
import com.nevidimka655.domain.lab_zip.usecase.GetSourceFileInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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