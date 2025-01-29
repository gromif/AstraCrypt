package io.gromif.astracrypt.profile.data.util.preview

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.annotation.RequiresApi

class NativePreviewUtil(
    private val context: Context,
) {

    @RequiresApi(Build.VERSION_CODES.Q)
    operator fun invoke(uri: Uri, size: Int): Bitmap {
        return context.contentResolver.loadThumbnail(
            /* uri = */ uri,
            /* size = */ Size(size, size),
            /* signal = */ null
        )
    }

}