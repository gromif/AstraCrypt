package io.gromif.tinkLab.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.gromif.astracrypt.utils.Parser
import io.gromif.astracrypt.utils.Serializer
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithKey
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializer
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithKey
import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.data.util.KeyGeneratorImpl
import io.gromif.tinkLab.data.util.KeyReaderImpl
import io.gromif.tinkLab.data.util.KeyWriterImpl
import io.gromif.tinkLab.data.util.TextAeadUtil
import io.gromif.tinkLab.domain.util.KeyGenerator
import io.gromif.tinkLab.domain.util.KeyReader
import io.gromif.tinkLab.domain.util.KeyWriter

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
        keySerializer: Serializer<KeyDto, String>
    ): KeyWriter = KeyWriterImpl(
        contentResolver = context.contentResolver,
        keysetParser = keysetParser,
        keysetSerializerWithKey = keysetSerializerWithKey,
        keySerializer = keySerializer
    )

    @Provides
    fun provideKeyReader(
        @ApplicationContext
        context: Context,
        keyParser: Parser<String, KeyDto>,
        keysetSerializer: KeysetSerializer,
        keysetParserWithKey: KeysetParserWithKey,
    ): KeyReader = KeyReaderImpl(
        contentResolver = context.contentResolver,
        keyParser = keyParser,
        keysetParserWithKey = keysetParserWithKey,
        keysetSerializer = keysetSerializer,
    )
}
