package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Parser
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.util.KeyParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object ParserModule {

    @Provides
    fun provideKeyParser(hexUtil: HexUtil): Parser<String, KeyDto> =
        KeyParser(hexUtil = hexUtil)

}