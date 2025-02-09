package io.gromif.astracrypt.files.data.factory.flags

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.utils.Mapper

private const val METADATA_KEY_VIDEO_WIDTH = MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH
private const val METADATA_KEY_VIDEO_HEIGHT = MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT

class VideoFlagsFactory(
    private val context: Context,
    private val uriMapper: Mapper<String, Uri>,
) {

    fun create(path: String): FileFlagsDto.Video? {
        val uri = uriMapper(path)
        val defaultVideoFlags = FileFlagsDto.Video()
        var resolution = defaultVideoFlags.resolution

        context.contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
            val media = MediaMetadataRetriever()
            try {
                media.setDataSource(fd.fileDescriptor)

                val width = media.extractMetadata(METADATA_KEY_VIDEO_WIDTH)
                val height = media.extractMetadata(METADATA_KEY_VIDEO_HEIGHT)
                resolution = "${width}x$height"
            } finally {
                media.release()
            }
        }

        val videoFlags = FileFlagsDto.Video(resolution = resolution)
        return videoFlags.takeIf { it != defaultVideoFlags }
    }

}