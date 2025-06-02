package io.gromif.astracrypt.profile.data.util.preview

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import coil3.ImageLoader
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.transformations
import coil3.size.Scale
import coil3.toBitmap
import io.gromif.astracrypt.profile.data.util.CenterCropTransformation

class CoilPreviewUtil(
    private val context: Context,
    private val imageLoader: ImageLoader,
) {

    suspend operator fun invoke(uri: Uri, size: Int): Bitmap {
        return imageLoader.execute(
            ImageRequest.Builder(context)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .size(size)
                .scale(Scale.FILL)
                .transformations(CenterCropTransformation())
                .data(uri)
                .build()
        ).image!!.toBitmap()
    }
}
