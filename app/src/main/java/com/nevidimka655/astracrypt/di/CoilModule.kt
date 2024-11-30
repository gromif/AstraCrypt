package com.nevidimka655.astracrypt.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.transition.CrossfadeTransition
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.utils.TinkCoilFetcherFactory
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
        io: Io
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(io = io)

    @Singleton
    @Provides
    fun provideVideoFrameDecoderFactory() = VideoFrameDecoder.Factory()

    @Singleton
    @Provides
    fun provideCrossfadeTransitionFactory() = CrossfadeTransition.Factory()

}