package com.nevidimka655.tink_lab.di

import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.usecase.CreateLabKeyUseCase
import com.nevidimka655.tink_lab.domain.usecase.GetFileAeadListUseCase
import com.nevidimka655.tink_lab.domain.usecase.GetTextAeadListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    fun provideCreateLabKeyUseCase(
        serializeKeysetByKeyService: SerializeKeysetByKeyService,
        sha256Service: Sha256Service,
        hexService: HexService
    ) = CreateLabKeyUseCase(
        serializeKeysetByKeyService = serializeKeysetByKeyService,
        sha256Service = sha256Service,
        hexService = hexService
    )

    @Provides
    fun provideGetFileAeadListUseCase(repository: Repository) =
        GetFileAeadListUseCase(repository = repository)

    @Provides
    fun provideGetTextAeadListUseCase(repository: Repository) =
        GetTextAeadListUseCase(repository = repository)

}