package com.nevidimka655.tink_lab.di

import android.content.Context
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.Parser
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.parsers.ParseKeysetByKeyService
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.mapper.DtoToKeyMapper
import com.nevidimka655.tink_lab.data.util.KeyGeneratorImpl
import com.nevidimka655.tink_lab.data.util.KeyReaderImpl
import com.nevidimka655.tink_lab.data.util.KeyWriterImpl
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyGenerator
import com.nevidimka655.tink_lab.domain.util.KeyReader
import com.nevidimka655.tink_lab.domain.util.KeyWriter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object UtilModule {

    @Provides
    fun provideKeyGenerator(
        serializeKeysetByKeyService: SerializeKeysetByKeyService,
        sha256Service: Sha256Service,
        hexService: HexService
    ): KeyGenerator = KeyGeneratorImpl(
        serializeKeysetByKeyService = serializeKeysetByKeyService,
        sha256Service = sha256Service,
        hexService = hexService
    )

    @Provides
    fun provideKeyWriter(
        @ApplicationContext
        context: Context,
        keyToDtoMapper: Mapper<Key, KeyDto>,
        stringToUriMapper: Mapper<String, Uri>,
        keySerializer: Serializer<KeyDto, String>
    ): KeyWriter = KeyWriterImpl(
        contentResolver = context.contentResolver,
        stringToUriMapper = stringToUriMapper,
        keyToDtoMapper = keyToDtoMapper,
        keySerializer = keySerializer
    )

    @Provides
    fun provideKeyReader(
        @ApplicationContext
        context: Context,
        stringToUriMapper: Mapper<String, Uri>,
        keyParser: Parser<String, KeyDto>,
        parseKeysetByKeyService: ParseKeysetByKeyService,
        dtoToKeyMapper: Mapper<KeyDto, Key>
    ): KeyReader = KeyReaderImpl(
        contentResolver = context.contentResolver,
        stringToUriMapper = stringToUriMapper,
        keyParser = keyParser,
        parseKeysetByKeyService = parseKeysetByKeyService,
        dtoToKeyMapper = dtoToKeyMapper,
    )

}