package io.gromif.astracrypt.files.di.coil

import android.content.Context
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.transformations
import coil3.size.Scale
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.util.coil.CenterCropTransformation
import javax.inject.Qualifier
import javax.inject.Singleton

private const val IMPORT_IMAGE_SIZE = 500

@Module
@InstallIn(SingletonComponent::class)
internal object ImportCoilModule {

    @ImportImageRequestBuilder
    @Singleton
    @Provides
    fun provideImageRequestBuilder(
        @ApplicationContext context: Context
    ): ImageRequest.Builder = ImageRequest.Builder(context)
        .diskCachePolicy(CachePolicy.DISABLED)
        .memoryCachePolicy(CachePolicy.DISABLED)
        .size(IMPORT_IMAGE_SIZE)
        .scale(Scale.FILL)
        .transformations(CenterCropTransformation())
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImportImageRequestBuilder
