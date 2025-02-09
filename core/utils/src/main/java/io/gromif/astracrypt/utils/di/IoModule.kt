package io.gromif.astracrypt.utils.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.utils.io.FilesUtil
import io.gromif.astracrypt.utils.io.Randomizer
import io.gromif.astracrypt.utils.io.WorkerSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object IoModule {

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