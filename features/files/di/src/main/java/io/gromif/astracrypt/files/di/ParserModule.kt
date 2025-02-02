package io.gromif.astracrypt.files.di

import com.nevidimka655.astracrypt.utils.Parser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.util.parser.ItemFlagsDtoParser

@Module
@InstallIn(SingletonComponent::class)
internal object ParserModule {

    @Provides
    fun provideItemFlagsDtoParser(): Parser<String, FileFlagsDto> = ItemFlagsDtoParser()

}