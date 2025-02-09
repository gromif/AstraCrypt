package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.util.mapper.FileItemMapper
import io.gromif.astracrypt.files.data.util.mapper.ItemDetailsMapper
import io.gromif.astracrypt.files.data.util.mapper.ItemFlagsMapper
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.Parser

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {

    @Provides
    fun provideItemFlagsMapper(): Mapper<FileFlagsDto, FileFlags> = ItemFlagsMapper()

    @Provides
    fun provideItemDetailsMapper(
        itemFlagsMapper: Mapper<FileFlagsDto, FileFlags>,
        itemFlagsDtoParser: Parser<String, FileFlagsDto>,
    ): Mapper<DetailsTuple, ItemDetails> = ItemDetailsMapper(
        itemFlagsMapper = itemFlagsMapper,
        itemFlagsDtoParser = itemFlagsDtoParser
    )

    @Provides
    fun provideFileItemMapper(): Mapper<FilesEntity, Item> = FileItemMapper()

}