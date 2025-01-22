package io.gromif.astracrypt.files.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.domain.model.FileFlags

class FileFlagsDtoMapper: Mapper<FileFlags, FileFlagsDto> {
    override fun invoke(item: FileFlags): FileFlagsDto {
        return when(item) {
            is FileFlags.Audio -> FileFlagsDto.Audio(
                title = item.title,
                sampleRate = item.sampleRate,
                author = item.author
            )
            is FileFlags.Image -> FileFlagsDto.Image(
                resolution = item.resolution
            )
            is FileFlags.Video -> FileFlagsDto.Video(
                resolution = item.resolution
            )
        }
    }
}