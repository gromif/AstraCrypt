package io.gromif.astracrypt.files.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.transition.CrossfadeTransition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.data.util.coil.TinkCoilFetcherFactory
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FilesCoilModule {

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
        @ApplicationContext context: Context,
        fileHandler: FileHandler,
        associatedDataManager: AssociatedDataManager,
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(
        fileHandler = fileHandler,
        associatedDataManager = associatedDataManager,
        cacheDir = context.cacheDir
    )

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FilesImageLoader