package com.nevidimka655.astracrypt.view.models

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import coil.size.Size
import coil.size.pxOrElse
import coil.transform.Transformation

class CenterCropTransformation : Transformation {
    override val cacheKey: String
        get() = ""

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