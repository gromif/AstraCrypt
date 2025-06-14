package io.gromif.astracrypt.files.di.coil

import android.content.Context
import coil3.ImageLoader
import coil3.request.CachePolicy
import coil3.request.transitionFactory
import coil3.transition.CrossfadeTransition
import coil3.video.VideoFrameDecoder
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
        fileHandler: FileHandler,
        associatedDataManager: AssociatedDataManager,
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(
        fileHandler = fileHandler,
        associatedDataManager = associatedDataManager,
    )
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FilesImageLoader
