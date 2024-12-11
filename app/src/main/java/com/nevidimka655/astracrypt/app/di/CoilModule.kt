package com.nevidimka655.astracrypt.app.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.transition.CrossfadeTransition
import com.nevidimka655.astracrypt.app.utils.AeadManager
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.app.utils.TinkCoilFetcherFactory
import com.nevidimka655.crypto.tink.KeysetFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoilModule {

    @Singleton
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        tinkCoilFetcherFactory: TinkCoilFetcherFactory,
        videoFrameDecoderFactory: VideoFrameDecoder.Factory,
        crossfadeTransitionFactory: CrossfadeTransition.Factory
    ): ImageLoader = ImageLoader.Builder(context)
        .components {
            add(tinkCoilFetcherFactory)
            add(videoFrameDecoderFactory)
        }
        .transitionFactory(crossfadeTransitionFactory)
        .diskCachePolicy(CachePolicy.DISABLED)
        .build()


    @Singleton
    @Provides
    fun provideTinkCoilFetcherFactory(
        io: Io,
        keysetFactory: KeysetFactory,
        aeadManager: AeadManager
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(
        io = io,
        aeadManager = aeadManager,
        keysetFactory = keysetFactory
    )

    @Singleton
    @Provides
    fun provideVideoFrameDecoderFactory() = VideoFrameDecoder.Factory()

    @Singleton
    @Provides
    fun provideCrossfadeTransitionFactory() = CrossfadeTransition.Factory()

}