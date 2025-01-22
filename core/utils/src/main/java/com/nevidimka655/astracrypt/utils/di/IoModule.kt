package com.nevidimka655.astracrypt.utils.di

import android.content.Context
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.astracrypt.utils.io.MediaMetadataRetrieverCompat
import com.nevidimka655.astracrypt.utils.io.Randomizer
import com.nevidimka655.astracrypt.utils.io.WorkerSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object IoModule {

    @Singleton
    @Provides
    fun provideMediaMetadataRetrieverCompat() = MediaMetadataRetrieverCompat()

    @Singleton
    @Provides
    fun provideWorkerSerializer() = WorkerSerializer()

    @Singleton
    @Provides
    fun provideFilesUtil(
        @ApplicationContext context: Context,
        randomizer: Randomizer
    ) = FilesUtil(
        context = context,
        randomizer = randomizer
    )

    @Singleton
    @Provides
    fun provideRandomizer() = Randomizer()

}