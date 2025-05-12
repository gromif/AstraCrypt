package io.gromif.tink_lab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.utils.Parser
import io.gromif.crypto.tink.core.encoders.HexEncoder
import io.gromif.tink_lab.data.dto.KeyDto
import io.gromif.tink_lab.data.util.KeyParser

@Module
@InstallIn(ViewModelComponent::class)
internal object ParserModule {

    @Provides
    fun provideKeyParser(hexEncoder: HexEncoder): Parser<String, KeyDto> =
        KeyParser(hexEncoder = hexEncoder)

}