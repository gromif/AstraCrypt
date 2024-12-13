package com.nevidimka655.astracrypt.app.di

import android.content.Context
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.app.utils.Randomizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IoModule {

    @Singleton
    @Provides
    fun provideIo(
        @ApplicationContext context: Context,
        randomizer: Randomizer
    ) = Io(
        context = context,
        randomizer = randomizer
    )
    
}