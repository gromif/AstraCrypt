package io.gromif.tinkLab.data.util

import android.content.ContentResolver
import android.net.Uri
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.Parser
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithKey
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializer
import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.util.KeyReader

class KeyReaderImpl(
    private val contentResolver: ContentResolver,
    private val keyParser: Parser<String, KeyDto>,
    private val keysetParserWithKey: KeysetParserWithKey,
    private val keysetSerializer: KeysetSerializer,
    private val dtoToKeyMapper: Mapper<KeyDto, Key>,
    private val stringToUriMapper: Mapper<String, Uri>
) : KeyReader {

    override fun invoke(
        uriString: String,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ): KeyReader.Result = run {
        val uri = stringToUriMapper(uriString)
        val keyDto: KeyDto
        try {
            contentResolver.openInputStream(uri)?.use {
                val encoded = it.readBytes().decodeToString()
                keyDto = keyParser(encoded)
            } ?: return KeyReader.Result.Error
            val keysetHandle = keysetParserWithKey(
                serializedKeyset = keyDto.encryptedKeyset,
                key = keysetPassword.toByteArray(),
                associatedData = keysetAssociatedData
            )
            val decryptedKeyDto = keyDto.copy(encryptedKeyset = keysetSerializer(keysetHandle))
            KeyReader.Result.Success(dtoToKeyMapper(decryptedKeyDto))
        } catch (_: Exception) {
            KeyReader.Result.Error
        }
    }
}
