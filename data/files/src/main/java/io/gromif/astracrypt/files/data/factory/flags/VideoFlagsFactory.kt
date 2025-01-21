package io.gromif.astracrypt.files.data.factory.flags

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.io.MediaMetadataRetrieverCompat
import io.gromif.astracrypt.files.data.dto.FileFlagsDto

class VideoFlagsFactory(
    private val context: Context,
    private val mediaMetadataRetrieverCompat: MediaMetadataRetrieverCompat,
    private val uriMapper: Mapper<String, Uri>,
) {

    fun create(path: String): FileFlagsDto.Video? {
        val uri = uriMapper(path)
        val defaultVideoFlags = FileFlagsDto.Video()
        mediaMetadataRetrieverCompat.setDataSource(context, uri)

        var resolution = defaultVideoFlags.resolution
        mediaMetadataRetrieverCompat.use {
            val width = it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
            val height = it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
            resolution = "${width}x$height"
        }
        val videoFlags = FileFlagsDto.Video(resolution = resolution)
        return videoFlags.takeIf { it != defaultVideoFlags }
    }

}