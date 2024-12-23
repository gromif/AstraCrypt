package com.nevidimka655.tink_lab.domain.usecase

import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.TinkLabKey

private val keysetAssociatedData = "labKey".toByteArray()

class CreateLabKeyUseCase(
    private val serializeKeysetByKeyService: SerializeKeysetByKeyService,
    private val sha256Service: Sha256Service,
    private val hexService: HexService
) {

    operator fun invoke(
        keysetPassword: String,
        dataType: DataType,
        aeadType: String
    ): TinkLabKey {
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
        return TinkLabKey(
            dataType = dataType,
            encryptedKeyset = serializedEncryptedKeyset,
            aeadType = aeadType,
            hash = keysetHash
        )
    }

}