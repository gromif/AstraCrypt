package io.gromif.astracrypt.files.data.factory.flags

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.utils.Mapper

class ImageFlagsFactory(
    private val contentResolver: ContentResolver,
    private val uriMapper: Mapper<String, Uri>,
) {

    fun create(path: String): FileFlagsDto.Image? {
        val uri = uriMapper(path)
        val default = FileFlagsDto.Image()
        val fileDescriptor = contentResolver.openFileDescriptor(uri, "r")

        var width = 0
        var height = 0
        var resolution = default.resolution

        fileDescriptor?.let {
            ExifInterface(it.fileDescriptor).run {
                width = getAttribute(ExifInterface.TAG_IMAGE_WIDTH)?.toIntOrNull() ?: 0
                height = getAttribute(ExifInterface.TAG_IMAGE_LENGTH)?.toIntOrNull() ?: 0
            }
        }
        if (width + height == 0) {
            fileDescriptor?.let {
                val bitmapOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeFileDescriptor(it.fileDescriptor, null, bitmapOptions)
                height = bitmapOptions.outHeight
                width = bitmapOptions.outWidth
            }
        }
        fileDescriptor?.close()

        if (width + height != 0) resolution = "${width}x$height"

        val imageFlags = FileFlagsDto.Image(resolution = resolution)
        return imageFlags.takeIf { it != default }
    }
}
