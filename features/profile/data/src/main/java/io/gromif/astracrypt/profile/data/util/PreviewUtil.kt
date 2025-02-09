package io.gromif.astracrypt.profile.data.util

import android.graphics.Bitmap
import android.net.Uri
import io.gromif.astracrypt.profile.data.util.preview.CoilPreviewUtil
import io.gromif.astracrypt.profile.data.util.preview.NativePreviewUtil
import io.gromif.astracrypt.utils.Api

class PreviewUtil(
    private val nativePreviewUtil: NativePreviewUtil,
    private val coilPreviewUtil: CoilPreviewUtil
) {

    suspend operator fun invoke(uri: Uri, size: Int): Bitmap {
        return if (Api.atLeast10()) nativePreviewUtil(uri, size) else {
            coilPreviewUtil(uri, size)
        }
    }

}