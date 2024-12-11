package com.nevidimka655.astracrypt.app.di.work

import android.content.Context
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.nevidimka655.astracrypt.app.config.AppConfig
import com.nevidimka655.astracrypt.app.utils.CenterCropTransformation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImportFilesModule {

    @Provides
    @Singleton
    fun provideDefaultCoilRequestBuilder(
        @ApplicationContext context: Context
    ): ImageRequest.Builder = ImageRequest.Builder(context)
        .diskCachePolicy(CachePolicy.DISABLED)
        .memoryCachePolicy(CachePolicy.DISABLED)
        .size(AppConfig.DB_THUMB_SIZE)
        .scale(Scale.FILL)
        .transformations(CenterCropTransformation())

}