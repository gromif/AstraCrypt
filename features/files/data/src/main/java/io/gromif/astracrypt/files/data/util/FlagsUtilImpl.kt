package io.gromif.astracrypt.files.data.util

import com.nevidimka655.astracrypt.utils.Serializer
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.factory.flags.AudioFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.ImageFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.VideoFlagsFactory
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.util.FlagsUtil

class FlagsUtilImpl(
    private val audioFlagsFactory: AudioFlagsFactory,
    private val imageFlagsFactory: ImageFlagsFactory,
    private val videoFlagsFactory: VideoFlagsFactory,
    private val serializer: Serializer<FileFlagsDto, String>
): FlagsUtil {

    override suspend fun getFlags(
        type: FileType,
        path: String,
    ): String? {
        val dto = when(type) {
            FileType.Photo -> imageFlagsFactory.create(path)
            FileType.Music -> audioFlagsFactory.create(path)
            FileType.Video -> videoFlagsFactory.create(path)
            else -> null
        }
        return dto?.let { serializer(it) }
    }

}