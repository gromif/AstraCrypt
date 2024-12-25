package com.nevidimka655.tink_lab.data.util

import android.content.ContentResolver
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.Parser
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.data.parsers.ParseKeysetByKeyService
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.mapper.DtoToKeyMapper
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyReader
import com.nevidimka655.tink_lab.domain.util.KeyWriter

class KeyReaderImpl(
    private val contentResolver: ContentResolver,
    private val keyParser: Parser<String, KeyDto>,
    private val parseKeysetByKeyService: ParseKeysetByKeyService,
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
            keyDto.encryptedKeyset!!.let {
                parseKeysetByKeyService.parse(
                    serializedKeyset = it,
                    key = keysetPassword.toByteArray(),
                    associatedData = keysetAssociatedData
                )
            }
            return KeyReader.Result.Success(dtoToKeyMapper(keyDto))
        } catch (_: Exception) {
            return KeyReader.Result.Error
        }
    }

}