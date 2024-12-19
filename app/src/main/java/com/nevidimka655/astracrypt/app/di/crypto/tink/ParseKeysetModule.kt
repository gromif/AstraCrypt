package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.domain.usecase.ParseKeysetByAeadUseCase
import com.nevidimka655.crypto.tink.domain.usecase.encoder.HexUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParseKeysetModule {

    @Singleton
    @Provides
    fun provideParseKeysetByAeadUseCase(
        hexUseCase: HexUseCase
    ) = ParseKeysetByAeadUseCase(hexUseCase = hexUseCase)
}