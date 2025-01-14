package io.gromif.astracrypt.files.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.transition.CrossfadeTransition
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.io.TinkCoilFetcherFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoilModule {

    @FilesImageLoader
    @Singleton
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        tinkCoilFetcherFactory: TinkCoilFetcherFactory
    ): ImageLoader = ImageLoader.Builder(context)
        .components {
            add(tinkCoilFetcherFactory)
            add(VideoFrameDecoder.Factory())
        }
        .transitionFactory(CrossfadeTransition.Factory())
        .diskCachePolicy(CachePolicy.DISABLED)
        .build()

    @Singleton
    @Provides
    fun provideTinkCoilFetcherFactory(
        filesUtil: FilesUtil,
        keysetManager: KeysetManager,
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(
        filesUtil = filesUtil,
        keysetManager = keysetManager
    )

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FilesImageLoader