package io.gromif.astracrypt.di.crypto.tink

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.encoders.HexEncoder
import io.gromif.crypto.tink.keyset.KeysetAeadFactory
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithAead
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParseKeysetModule {

    @Singleton
    @Provides
    fun provideKeysetParser(
        hexEncoder: HexEncoder
    ) = KeysetParser(encoder = hexEncoder)

    @Singleton
    @Provides
    fun provideParseKeysetByAead(
        hexEncoder: HexEncoder
    ) = KeysetParserWithAead(encoder = hexEncoder)

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