package io.gromif.tink_lab.di

import android.content.Context
import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.Parser
import io.gromif.astracrypt.utils.Serializer
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithKey
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializer
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithKey
import io.gromif.tink_lab.data.dto.KeyDto
import io.gromif.tink_lab.data.util.KeyGeneratorImpl
import io.gromif.tink_lab.data.util.KeyReaderImpl
import io.gromif.tink_lab.data.util.KeyWriterImpl
import io.gromif.tink_lab.data.util.TextAeadUtil
import io.gromif.tink_lab.domain.model.Key
import io.gromif.tink_lab.domain.util.KeyGenerator
import io.gromif.tink_lab.domain.util.KeyReader
import io.gromif.tink_lab.domain.util.KeyWriter

@Module
@InstallIn(ViewModelComponent::class)
internal object UtilModule {

    @Provides
    fun provideTextAeadUtil() = TextAeadUtil()

    @Provides
    fun provideKeyGenerator(keysetSerializer: KeysetSerializer): KeyGenerator =
        KeyGeneratorImpl(keysetSerializer = keysetSerializer)

    @Provides
    fun provideKeyWriter(
        @ApplicationContext
        context: Context,
        keysetParser: KeysetParser,
        keysetSerializerWithKey: KeysetSerializerWithKey,
        keyToDtoMapper: Mapper<Key, KeyDto>,
        stringToUriMapper: Mapper<String, Uri>,
        keySerializer: Serializer<KeyDto, String>
    ): KeyWriter = KeyWriterImpl(
        contentResolver = context.contentResolver,
        keysetParser = keysetParser,
        keysetSerializerWithKey = keysetSerializerWithKey,
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
        keysetSerializer: KeysetSerializer,
        keysetParserWithKey: KeysetParserWithKey,
        dtoToKeyMapper: Mapper<KeyDto, Key>
    ): KeyReader = KeyReaderImpl(
        contentResolver = context.contentResolver,
        stringToUriMapper = stringToUriMapper,
        keyParser = keyParser,
        keysetParserWithKey = keysetParserWithKey,
        keysetSerializer = keysetSerializer,
        dtoToKeyMapper = dtoToKeyMapper,
    )

}