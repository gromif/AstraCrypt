package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.data.keyset.KeysetAeadFactory
import com.nevidimka655.crypto.tink.data.parsers.ParseKeysetByAeadService
import com.nevidimka655.crypto.tink.data.parsers.ParseKeysetByKeyService
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

    @Singleton
    @Provides
    fun provideParseKeysetByKey(
        parseKeysetByAeadService: ParseKeysetByAeadService,
        keysetAeadFactory: KeysetAeadFactory
    ) = ParseKeysetByKeyService(
        parseKeysetByAeadService = parseKeysetByAeadService,
        keysetAeadFactory = keysetAeadFactory
    )
}