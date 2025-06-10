package io.gromif.tinkLab.data.util

import android.content.ContentResolver
import android.net.Uri
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.Serializer
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithKey
import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.util.KeyWriter

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
