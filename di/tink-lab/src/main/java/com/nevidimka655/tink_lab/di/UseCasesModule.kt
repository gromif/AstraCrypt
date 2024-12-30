package com.nevidimka655.tink_lab.di

import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.usecase.CreateLabKeyUseCase
import com.nevidimka655.tink_lab.domain.usecase.GetFileAeadListUseCase
import com.nevidimka655.tink_lab.domain.usecase.GetTextAeadListUseCase
import com.nevidimka655.tink_lab.domain.usecase.LoadKeyUseCase
import com.nevidimka655.tink_lab.domain.usecase.SaveKeyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCasesModule {

    @Provides
    fun provideCreateLabKeyUseCase(repository: Repository) =
        CreateLabKeyUseCase(repository = repository)

    @Provides
    fun provideGetFileAeadListUseCase(repository: Repository) =
        GetFileAeadListUseCase(repository = repository)

    @Provides
    fun provideGetTextAeadListUseCase(repository: Repository) =
        GetTextAeadListUseCase(repository = repository)

    @Provides
    fun provideSaveKeyUseCase(repository: Repository) = SaveKeyUseCase(repository = repository)

    @Provides
    fun provideLoadKeyUseCase(repository: Repository) = LoadKeyUseCase(repository = repository)

}