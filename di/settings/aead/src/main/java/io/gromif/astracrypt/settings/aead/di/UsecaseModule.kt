package io.gromif.astracrypt.settings.aead.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.settings.aead.domain.repository.Repository
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadLargeStreamTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadSmallStreamTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetNotesAeadTemplateUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.SetNotesAeadUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UsecaseModule {

    @Provides
    fun provideGetAeadLargeStreamTemplateListUseCase(
        repository: Repository
    ): GetAeadLargeStreamTemplateListUseCase = GetAeadLargeStreamTemplateListUseCase(
        repository = repository
    )

    @Provides
    fun provideGetAeadSmallStreamTemplateListUseCase(
        repository: Repository
    ): GetAeadSmallStreamTemplateListUseCase = GetAeadSmallStreamTemplateListUseCase(
        repository = repository
    )

    @Provides
    fun provideGetAeadTemplateListUseCase(
        repository: Repository
    ): GetAeadTemplateListUseCase = GetAeadTemplateListUseCase(
        repository = repository
    )

    @Provides
    fun provideGetNotesAeadTemplateUseCase(
        repository: Repository
    ): GetNotesAeadTemplateUseCase = GetNotesAeadTemplateUseCase(
        repository = repository
    )

    @Provides
    fun provideSetNotesAeadUseCase(repository: Repository): SetNotesAeadUseCase =
        SetNotesAeadUseCase(repository = repository)

}