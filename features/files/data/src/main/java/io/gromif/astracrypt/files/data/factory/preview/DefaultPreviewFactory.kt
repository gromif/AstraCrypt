package io.gromif.astracrypt.files.data.factory.preview

import android.net.Uri
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.toBitmap
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
        val bitmap = imageLoader.execute(request).image?.toBitmap()
        return bitmap?.let {
            bitmapCompressor(bitmap = it, quality = 70)
        }
    }
}
