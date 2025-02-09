package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.util.parser.ItemFlagsDtoParser
import io.gromif.astracrypt.utils.Parser

@Module
@InstallIn(SingletonComponent::class)
internal object ParserModule {

    @Provides
    fun provideItemFlagsDtoParser(): Parser<String, FileFlagsDto> = ItemFlagsDtoParser()

}