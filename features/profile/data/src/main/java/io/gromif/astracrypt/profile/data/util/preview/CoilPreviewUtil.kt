package io.gromif.astracrypt.profile.data.util.preview

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
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
        ).drawable!!.toBitmap()
    }

}