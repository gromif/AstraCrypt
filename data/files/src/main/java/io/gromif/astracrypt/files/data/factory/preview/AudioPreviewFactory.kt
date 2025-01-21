package io.gromif.astracrypt.files.data.factory.preview

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.io.MediaMetadataRetrieverCompat
import io.gromif.astracrypt.files.data.util.BitmapCompressor

class AudioPreviewFactory(
    private val uriMapper: Mapper<String, Uri>,
    private val contentResolver: ContentResolver,
    private val mediaMetadataRetrieverCompat: MediaMetadataRetrieverCompat,
    private val imageRequestBuilder: ImageRequest.Builder,
    private val imageLoader: ImageLoader,
    private val bitmapCompressor: BitmapCompressor,
) {

    suspend fun create(path: String): ByteArray? {
        val uri = uriMapper(path)
        val fd = contentResolver.openFileDescriptor(uri, "r") ?: return null
        var bitmap: Bitmap? = null
        mediaMetadataRetrieverCompat.apply { setDataSource(fd.fileDescriptor) }.use { data ->
            data.embeddedPicture?.let {
                val request = imageRequestBuilder.data(it).build()
                val bitmapDrawable = imageLoader.execute(request).drawable as BitmapDrawable?
                bitmap = bitmapDrawable?.bitmap
            }
        }
        fd.close()
        return bitmap?.let {
            bitmapCompressor(bitmap = it, quality = 70)
        }
    }

}