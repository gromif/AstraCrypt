package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.data.parsers.ParseKeysetByAeadService
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
    fun provideParseKeysetByAead(
        hexService: HexService
    ) = ParseKeysetByAeadService(hexService = hexService)
}