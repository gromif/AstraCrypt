package io.gromif.astracrypt.app.di.crypto.tink

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.core.encoders.HexEncoder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncoderModule {

    @Singleton
    @Provides
    fun provideHexService() = HexEncoder()

    @Singleton
    @Provides
    fun provideBase64Service() = Base64Encoder()
}