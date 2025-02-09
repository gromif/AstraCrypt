package io.gromif.astracrypt.utils.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.utils.app.AppComponentService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Singleton
    @Provides
    fun provideAppComponentService(
        @ApplicationContext context: Context,
    ) = AppComponentService(context = context)

}