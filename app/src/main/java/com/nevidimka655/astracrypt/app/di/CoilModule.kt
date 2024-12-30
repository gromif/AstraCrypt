package com.nevidimka655.astracrypt.app.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.transition.CrossfadeTransition
import com.nevidimka655.astracrypt.data.crypto.AeadManager
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.astracrypt.data.io.TinkCoilFetcherFactory
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

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
        filesUtil: FilesUtil,
        keysetManager: KeysetManager,
        aeadManager: AeadManager
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(
        filesUtil = filesUtil,
        aeadManager = aeadManager,
        keysetManager = keysetManager
    )

    @Singleton
    @Provides
    fun provideVideoFrameDecoderFactory() = VideoFrameDecoder.Factory()

    @Singleton
    @Provides
    fun provideCrossfadeTransitionFactory() = CrossfadeTransition.Factory()

}