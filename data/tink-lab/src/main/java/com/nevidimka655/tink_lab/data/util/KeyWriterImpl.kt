package com.nevidimka655.tink_lab.data.util

import android.content.ContentResolver
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.core.parsers.KeysetParser
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializerWithKey
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyWriter

class KeyWriterImpl(
    private val contentResolver: ContentResolver,
    private val keysetParser: KeysetParser,
    private val keysetSerializerWithKey: KeysetSerializerWithKey,
    private val stringToUriMapper: Mapper<String, Uri>,
    private val keyToDtoMapper: Mapper<Key, KeyDto>,
    private val keySerializer: Serializer<KeyDto, String>
) : KeyWriter {

    override fun invoke(
        uriString: String,
        key: Key,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ) {
        val uri = stringToUriMapper(uriString)
        val keysetHandle = keysetParser(key.rawKeyset)
        val serializedKeysetWithKey = keysetSerializerWithKey(
            keysetHandle = keysetHandle,
            key = keysetPassword.toByteArray(),
            associatedData = keysetAssociatedData
        )
        val keyDto = keyToDtoMapper(key).copy(encryptedKeyset = serializedKeysetWithKey)
        val serializedKey = keySerializer(keyDto)
        val mode = "wt"
        contentResolver.openOutputStream(uri, mode)?.use {
            val bytes = serializedKey.toByteArray()
            it.write(bytes)
        }
    }

}