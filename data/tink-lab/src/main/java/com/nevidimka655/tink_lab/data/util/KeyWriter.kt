package com.nevidimka655.tink_lab.data.util

import android.content.ContentResolver
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.tink_lab.data.dto.KeyDto

class KeyWriter(
    private val contentResolver: ContentResolver,
    private val keySerializer: Serializer<KeyDto, String>
) {

    operator fun invoke(uri: Uri, keyDto: KeyDto) {
        val serializedKey = keySerializer(keyDto)
        val mode = "wt"
        contentResolver.openOutputStream(uri, mode)?.use {
            val bytes = serializedKey.toByteArray()
            it.write(bytes)
        }
    }

}