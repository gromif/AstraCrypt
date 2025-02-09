package io.gromif.astracrypt.utils.di

import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.mapper.StringToUriMapper
import io.gromif.astracrypt.utils.mapper.UriToStringMapper
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