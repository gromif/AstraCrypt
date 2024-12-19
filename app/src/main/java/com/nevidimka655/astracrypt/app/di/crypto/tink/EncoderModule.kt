package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.core.encoders.HexService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EncoderModule {

    @Provides
    fun provideHexService() = HexService()
}