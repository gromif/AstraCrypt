package io.gromif.tinkLab.data.util

import android.content.ContentResolver
import androidx.core.net.toUri
import io.gromif.astracrypt.utils.Parser
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithKey
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializer
import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.data.mapper.toKey
import io.gromif.tinkLab.domain.model.result.ReadKeyResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class KeyReader(
    private val dispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver,
    private val keyParser: Parser<String, KeyDto>,
    private val keysetParserWithKey: KeysetParserWithKey,
    private val keysetSerializer: KeysetSerializer,
) {

    suspend operator fun invoke(
        uriString: String,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ): ReadKeyResult = withContext(dispatcher) {
        val uri = uriString.toUri()
        val keyDto: KeyDto
        try {
            contentResolver.openInputStream(uri)?.use {
                val encoded = it.readBytes().decodeToString()
                keyDto = keyParser(encoded)
            } ?: return@withContext ReadKeyResult.Error
            val keysetHandle = keysetParserWithKey(
                serializedKeyset = keyDto.encryptedKeyset,
                key = keysetPassword.toByteArray(),
                associatedData = keysetAssociatedData
            )
            val decryptedKeyDto = keyDto.copy(encryptedKeyset = keysetSerializer(keysetHandle))
            ReadKeyResult.Success(decryptedKeyDto.toKey())
        } catch (_: Exception) {
            ReadKeyResult.Error
        }
    }
}
