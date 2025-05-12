package io.gromif.tink_lab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.tink_lab.data.repository.RepositoryImpl
import io.gromif.tink_lab.data.util.TextAeadUtil
import io.gromif.tink_lab.domain.model.Repository
import io.gromif.tink_lab.domain.util.KeyGenerator
import io.gromif.tink_lab.domain.util.KeyReader
import io.gromif.tink_lab.domain.util.KeyWriter

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(
        textAeadUtil: TextAeadUtil,
        keysetParser: KeysetParser,
        keyGenerator: KeyGenerator,
        keyWriter: KeyWriter,
        keyReader: KeyReader,
        base64Encoder: Base64Encoder,
    ): Repository = RepositoryImpl(
        textAeadUtil = textAeadUtil,
        keysetParser = keysetParser,
        keyGenerator = keyGenerator,
        keyWriter = keyWriter,
        keyReader = keyReader,
        base64Encoder = base64Encoder
    )

}