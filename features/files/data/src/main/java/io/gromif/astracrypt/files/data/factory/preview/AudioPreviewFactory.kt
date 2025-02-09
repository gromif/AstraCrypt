package io.gromif.astracrypt.files.data.factory.preview

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.BitmapCompressor

class AudioPreviewFactory(
    private val uriMapper: Mapper<String, Uri>,
    private val contentResolver: ContentResolver,
    private val imageRequestBuilder: ImageRequest.Builder,
    private val imageLoader: ImageLoader,
    private val bitmapCompressor: BitmapCompressor,
) {

    suspend fun create(path: String): ByteArray? {
        val uri = uriMapper(path)
        var bitmap: Bitmap? = null

        contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
            val media = MediaMetadataRetriever()
            try {
                media.setDataSource(fd.fileDescriptor)

                media.embeddedPicture?.let {
                    val request = imageRequestBuilder.data(it).build()
                    val bitmapDrawable = imageLoader.execute(request).drawable as BitmapDrawable?
                    bitmap = bitmapDrawable?.bitmap
                }
            } finally {
                media.release()
            }
        }

        return bitmap?.let {
            bitmapCompressor(bitmap = it, quality = 70)
        }
    }

}