package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.domain.usecase.CreateKeysetAeadUseCase
import com.nevidimka655.crypto.tink.domain.usecase.CreateKeysetKeyUseCase
import com.nevidimka655.crypto.tink.domain.usecase.SerializeKeysetByAeadUseCase
import com.nevidimka655.crypto.tink.domain.usecase.SerializeKeysetByKeyUseCase
import com.nevidimka655.crypto.tink.domain.usecase.encoder.HexUseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha384UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializeKeysetModule {

    @Singleton
    @Provides
    fun provideSerializeKeysetByKeyUseCase(
        serializeKeysetByAeadUseCase: SerializeKeysetByAeadUseCase,
        createKeysetAeadUseCase: CreateKeysetAeadUseCase
    ) = SerializeKeysetByKeyUseCase(
        serializeKeysetByAeadUseCase = serializeKeysetByAeadUseCase,
        createKeysetAeadUseCase = createKeysetAeadUseCase
    )

    @Singleton
    @Provides
    fun provideSerializeKeysetByAeadUseCase(
        hexUseCase: HexUseCase
    ) = SerializeKeysetByAeadUseCase(hexUseCase = hexUseCase)

    @Singleton
    @Provides
    fun provideCreateKeysetAeadUseCase(createKeysetKeyUseCase: CreateKeysetKeyUseCase) =
        CreateKeysetAeadUseCase(createKeysetKeyUseCase = createKeysetKeyUseCase)

    @Singleton
    @Provides
    fun provideCreateKeysetKeyUseCase(sha384UseCase: Sha384UseCase) =
        CreateKeysetKeyUseCase(sha384UseCase = sha384UseCase)
}