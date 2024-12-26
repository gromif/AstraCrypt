package com.nevidimka655.tink_lab.data.util

import android.content.ContentResolver
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.Parser
import com.nevidimka655.crypto.tink.core.parsers.KeysetParserWithKey
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializer
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyReader

class KeyReaderImpl(
    private val contentResolver: ContentResolver,
    private val keyParser: Parser<String, KeyDto>,
    private val keysetParserWithKey: KeysetParserWithKey,
    private val keysetSerializer: KeysetSerializer,
    private val dtoToKeyMapper: Mapper<KeyDto, Key>,
    private val stringToUriMapper: Mapper<String, Uri>
): KeyReader {

    override fun invoke(
        uriString: String,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ): KeyReader.Result {
        val uri = stringToUriMapper(uriString)
        val keyDto: KeyDto
        try {
            contentResolver.openInputStream(uri)?.use {
                val encoded = it.readAllBytes().decodeToString()
                keyDto = keyParser(encoded)
            } ?: return KeyReader.Result.Error
            val keysetHandle = keysetParserWithKey(
                serializedKeyset = keyDto.encryptedKeyset,
                key = keysetPassword.toByteArray(),
                associatedData = keysetAssociatedData
            )
            val decryptedKeyDto = keyDto.copy(encryptedKeyset = keysetSerializer(keysetHandle))
            return KeyReader.Result.Success(dtoToKeyMapper(decryptedKeyDto))
        } catch (_: Exception) {
            return KeyReader.Result.Error
        }
    }

}