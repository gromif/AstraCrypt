package com.nevidimka655.astracrypt.app.di.crypto.tink

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.encoders.HexUtil
import io.gromif.crypto.tink.core.parsers.KeysetParser
import io.gromif.crypto.tink.core.parsers.KeysetParserWithAead
import io.gromif.crypto.tink.core.parsers.KeysetParserWithKey
import io.gromif.crypto.tink.data.keyset.KeysetAeadFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParseKeysetModule {

    @Singleton
    @Provides
    fun provideKeysetParser(
        hexUtil: HexUtil
    ) = KeysetParser(hexUtil = hexUtil)

    @Singleton
    @Provides
    fun provideParseKeysetByAead(
        hexUtil: HexUtil
    ) = KeysetParserWithAead(hexUtil = hexUtil)

    @Singleton
    @Provides
    fun provideParseKeysetByKey(
        keysetParserWithAead: KeysetParserWithAead,
        keysetAeadFactory: KeysetAeadFactory
    ) = KeysetParserWithKey(
        keysetParserWithAead = keysetParserWithAead,
        keysetAeadFactory = keysetAeadFactory
    )
}