package io.gromif.astracrypt.files.data.factory.preview

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.BitmapCompressor

class DefaultPreviewFactory(
    private val uriMapper: Mapper<String, Uri>,
    private val imageRequestBuilder: ImageRequest.Builder,
    private val imageLoader: ImageLoader,
    private val bitmapCompressor: BitmapCompressor,
) {

    suspend fun create(path: String): ByteArray? {
        val uri = uriMapper(path)
        val request = imageRequestBuilder.data(uri).build()
        val bitmapDrawable = imageLoader.execute(request).drawable as BitmapDrawable?
        val bitmap = bitmapDrawable?.bitmap
        return bitmap?.let {
            bitmapCompressor(bitmap = it, quality = 70)
        }
    }
}
