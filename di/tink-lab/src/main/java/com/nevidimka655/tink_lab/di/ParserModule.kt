package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Parser
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.util.KeyParser
import com.nevidimka655.tink_lab.data.util.KeySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ParserModule {

    @Provides
    fun provideKeyParser(hexService: HexService): Parser<String, KeyDto> =
        KeyParser(hexService = hexService)

}