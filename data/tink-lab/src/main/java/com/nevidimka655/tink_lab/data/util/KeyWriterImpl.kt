package com.nevidimka655.tink_lab.data.util

import android.content.ContentResolver
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyWriter

class KeyWriterImpl(
    private val contentResolver: ContentResolver,
    private val stringToUriMapper: Mapper<String, Uri>,
    private val keyToDtoMapper: Mapper<Key, KeyDto>,
    private val keySerializer: Serializer<KeyDto, String>
): KeyWriter {

    override fun invoke(uriString: String, key: Key) {
        val uri = stringToUriMapper(uriString)
        val keyDto = keyToDtoMapper(key)
        val serializedKey = keySerializer(keyDto)
        val mode = "wt"
        contentResolver.openOutputStream(uri, mode)?.use {
            val bytes = serializedKey.toByteArray()
            it.write(bytes)
        }
    }

}