package io.gromif.tink_lab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.tink_lab.data.service.DefaultAeadTextService
import io.gromif.tink_lab.data.util.TextAeadUtil
import io.gromif.tink_lab.domain.service.AeadTextService
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
internal object ServiceModule {

    @ViewModelScoped
    @Provides
    fun provideAeadTextService(
        textAeadUtil: TextAeadUtil,
        keysetParser: KeysetParser,
        base64Encoder: Base64Encoder
    ): AeadTextService = DefaultAeadTextService(
        defaultDispatcher = Dispatchers.Default,
        textAeadUtil = textAeadUtil,
        keysetParser = keysetParser,
        base64Encoder = base64Encoder
    )

}
