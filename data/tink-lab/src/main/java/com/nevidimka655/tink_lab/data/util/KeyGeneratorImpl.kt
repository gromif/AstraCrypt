package com.nevidimka655.tink_lab.data.util

import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyGenerator

class KeyGeneratorImpl(
    private val serializeKeysetByKeyService: SerializeKeysetByKeyService,
    private val sha256Service: Sha256Service,
    private val hexService: HexService
): KeyGenerator {

    override fun invoke(
        keysetPassword: String,
        keysetAssociatedData: ByteArray,
        dataType: DataType,
        aeadType: String
    ): Key {
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
        return Key(
            dataType = dataType,
            encryptedKeyset = serializedEncryptedKeyset,
            aeadType = aeadType,
            hash = keysetHash
        )
    }

}