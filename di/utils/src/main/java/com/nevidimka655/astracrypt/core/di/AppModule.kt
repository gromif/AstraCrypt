package com.nevidimka655.astracrypt.core.di

import android.content.Context
import com.nevidimka655.astracrypt.utils.app.AppComponentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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