package io.gromif.astracrypt.files.data.util

import com.nevidimka655.astracrypt.utils.Serializer
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.factory.flags.AudioFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.ImageFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.VideoFlagsFactory
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.util.FlagsUtil

class FlagsUtilImpl(
    private val audioFlagsFactory: AudioFlagsFactory,
    private val imageFlagsFactory: ImageFlagsFactory,
    private val videoFlagsFactory: VideoFlagsFactory,
    private val serializer: Serializer<FileFlagsDto, String>
): FlagsUtil {

    override suspend fun getFlags(
        type: ItemType,
        path: String,
    ): String? {
        val dto = when(type) {
            ItemType.Photo -> imageFlagsFactory.create(path)
            ItemType.Music -> audioFlagsFactory.create(path)
            ItemType.Video -> videoFlagsFactory.create(path)
            else -> null
        }
        return dto?.let { serializer(it) }
    }

}