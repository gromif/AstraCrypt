package io.gromif.tinkLab.data.util

import android.content.ContentResolver
import androidx.core.net.toUri
import io.gromif.astracrypt.utils.Serializer
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithKey
import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.data.mapper.toDto
import io.gromif.tinkLab.domain.model.Key
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class KeyWriterImpl(
    private val dispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver,
    private val keysetParser: KeysetParser,
    private val keysetSerializerWithKey: KeysetSerializerWithKey,
    private val keySerializer: Serializer<KeyDto, String>
) {

    suspend operator fun invoke(
        uriString: String,
        key: Key,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ): Unit = withContext(dispatcher) {
        val uri = uriString.toUri()
        val keysetHandle = keysetParser(key.rawKeyset)
        val serializedKeysetWithKey = keysetSerializerWithKey(
            keysetHandle = keysetHandle,
            key = keysetPassword.toByteArray(),
            associatedData = keysetAssociatedData
        )
        val keyDto = key.toDto().copy(encryptedKeyset = serializedKeysetWithKey)
        val serializedKey = keySerializer(keyDto)
        val mode = "wt"
        contentResolver.openOutputStream(uri, mode)?.use {
            val bytes = serializedKey.toByteArray()
            it.write(bytes)
        }
    }
}
