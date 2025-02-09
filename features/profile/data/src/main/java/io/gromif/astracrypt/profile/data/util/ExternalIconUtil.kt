package io.gromif.astracrypt.profile.data.util

import android.net.Uri
import io.gromif.astracrypt.utils.io.BitmapCompressor
import io.gromif.crypto.tink.domain.KeysetTemplates

class ExternalIconUtil(
    private val previewUtil: PreviewUtil,
    private val bitmapCompressor: BitmapCompressor,
    private val fileUtil: FileUtil,
) {

    suspend fun saveFromUri(
        uri: Uri,
        quality: Int,
        size: Int,
        aead: Int
    ) {
        val bitmap = previewUtil(uri, size)
        val compressedPreview = bitmapCompressor(bitmap, quality)
        fileUtil.recreate()
        fileUtil.write(
            bytes = compressedPreview,
            aeadTemplate = KeysetTemplates.Stream.entries.getOrNull(aead)
        )
    }

    suspend fun changeAead(oldAead: KeysetTemplates.Stream?, newAead: KeysetTemplates.Stream?) {
        fileUtil.changeAead(oldAead, newAead)
    }

}