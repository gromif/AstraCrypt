package com.nevidimka655.astracrypt.app.di.crypto.tink

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.encoders.Base64Util
import io.gromif.crypto.tink.encoders.HexUtil

@Module
@InstallIn(SingletonComponent::class)
object EncoderModule {

    @Provides
    fun provideHexService() = HexUtil()

    @Provides
    fun provideBase64Service() = Base64Util()
}