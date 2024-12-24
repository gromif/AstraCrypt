package com.nevidimka655.tink_lab.data.util

import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.mapper.DataTypeToIdMapper
import com.nevidimka655.tink_lab.domain.model.DataType

class KeyFactory(
    private val serializeKeysetByKeyService: SerializeKeysetByKeyService,
    private val sha256Service: Sha256Service,
    private val hexService: HexService,
    private val dataTypeToIdMapper: Mapper<DataType, Int>
) {

    fun create(
        keysetPassword: String,
        keysetAssociatedData: ByteArray,
        dataType: DataType,
        aeadType: String
    ): KeyDto {
        val template = KeyTemplates.get(
            if (dataType == DataType.Files) "${aeadType}_1MB" else aeadType
        )
        val keysetHandle = KeysetHandle.generateNew(template)
        val serializedEncryptedKeyset = serializeKeysetByKeyService.serialize(
            keysetHandle = keysetHandle,
            key = keysetPassword.toByteArray(),
            associatedData = keysetAssociatedData
        )
        val keysetHashArray = sha256Service.compute(
            value = serializedEncryptedKeyset.toByteArray()
        )
        val keysetHash = hexService.encode(bytes = keysetHashArray)
        return KeyDto(
            dataTypeId = dataTypeToIdMapper(dataType),
            encryptedKeyset = serializedEncryptedKeyset,
            aeadType = aeadType,
            hash = keysetHash
        )
    }

}