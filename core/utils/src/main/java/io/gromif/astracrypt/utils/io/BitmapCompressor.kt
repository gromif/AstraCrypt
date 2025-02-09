package io.gromif.astracrypt.utils.io

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class BitmapCompressor(
    private val compressFormat: Bitmap.CompressFormat
) {

    operator fun invoke(bitmap: Bitmap, quality: Int): ByteArray {
        val compressedByteStream = ByteArrayOutputStream().also {
            bitmap.compress(compressFormat, quality, it)
        }
        return compressedByteStream.toByteArray()
    }

}