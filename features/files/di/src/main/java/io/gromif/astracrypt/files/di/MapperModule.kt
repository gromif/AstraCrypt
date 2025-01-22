package io.gromif.astracrypt.files.di

import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.mapper.FileFlagsDtoMapper
import io.gromif.astracrypt.files.data.mapper.FileItemMapper
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.files.domain.model.FileItem

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {

    @Provides
    fun provideFileFlagsDtoMapper(): Mapper<FileFlags, FileFlagsDto> = FileFlagsDtoMapper()

    @Provides
    fun provideFileItemMapper(): Mapper<FilesEntity, FileItem> = FileItemMapper()

}