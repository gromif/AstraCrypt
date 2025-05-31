package io.gromif.astracrypt.files.di.coil

import android.content.Context
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.util.coil.CenterCropTransformation
import javax.inject.Qualifier
import javax.inject.Singleton

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
        .size(500)
        .scale(Scale.FILL)
        .transformations(CenterCropTransformation())
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImportImageRequestBuilder
