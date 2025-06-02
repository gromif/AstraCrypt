package io.gromif.astracrypt.profile.data.util

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import coil3.size.Size
import coil3.size.pxOrElse
import coil3.transform.Transformation

class CenterCropTransformation : Transformation() {
    override val cacheKey: String
        get() = "profile-icon-coil"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val min = minOf(size.height.pxOrElse { 0 }, size.width.pxOrElse { 0 })
        return ThumbnailUtils.extractThumbnail(
            input,
            min,
            min,
            ThumbnailUtils.OPTIONS_RECYCLE_INPUT
        )
    }
}
