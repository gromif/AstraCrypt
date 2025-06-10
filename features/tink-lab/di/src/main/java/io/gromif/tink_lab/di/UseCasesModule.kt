package io.gromif.tink_lab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.tinkLab.domain.model.Repository
import io.gromif.tinkLab.domain.service.AeadTextService
import io.gromif.tinkLab.domain.usecase.CreateLabKeyUseCase
import io.gromif.tinkLab.domain.usecase.DecryptTextUseCase
import io.gromif.tinkLab.domain.usecase.EncryptTextUseCase
import io.gromif.tinkLab.domain.usecase.GetFileAeadListUseCase
import io.gromif.tinkLab.domain.usecase.GetTextAeadListUseCase
import io.gromif.tinkLab.domain.usecase.LoadKeyUseCase
import io.gromif.tinkLab.domain.usecase.ParseKeysetUseCase
import io.gromif.tinkLab.domain.usecase.SaveKeyUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCasesModule {

    @Provides
    fun provideDecryptTextUseCase(aeadTextService: AeadTextService) =
        DecryptTextUseCase(aeadTextService = aeadTextService)

    @Provides
    fun provideEncryptTextUseCase(aeadTextService: AeadTextService) =
        EncryptTextUseCase(aeadTextService = aeadTextService)

    @Provides
    fun provideParseKeysetUseCase(aeadTextService: AeadTextService) =
        ParseKeysetUseCase(aeadTextService = aeadTextService)

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