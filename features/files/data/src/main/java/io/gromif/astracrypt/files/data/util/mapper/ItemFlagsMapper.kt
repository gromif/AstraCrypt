package io.gromif.astracrypt.files.data.util.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.domain.model.FileFlags

class ItemFlagsMapper: Mapper<FileFlagsDto, FileFlags> {
    override fun invoke(item: FileFlagsDto): FileFlags {
        return when(item) {
            is FileFlagsDto.Audio -> FileFlags.Audio(
                title = item.title,
                sampleRate = item.sampleRate,
                author = item.author
            )
            is FileFlagsDto.Image -> FileFlags.Image(
                resolution = item.resolution
            )
            is FileFlagsDto.Video -> FileFlags.Video(
                resolution = item.resolution
            )
        }
    }
}