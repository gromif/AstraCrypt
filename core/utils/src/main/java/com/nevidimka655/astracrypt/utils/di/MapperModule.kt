package com.nevidimka655.astracrypt.utils.di

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.mapper.StringToUriMapper
import com.nevidimka655.astracrypt.utils.mapper.UriToStringMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {

    @Singleton
    @Provides
    fun provideUriToStringMapper(): Mapper<Uri, String> = UriToStringMapper()

    @Singleton
    @Provides
    fun provideStringToUriMapper(): Mapper<String, Uri> = StringToUriMapper()

}