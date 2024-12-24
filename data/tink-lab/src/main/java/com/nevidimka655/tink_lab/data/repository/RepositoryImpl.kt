package com.nevidimka655.tink_lab.data.repository

import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.model.Key

private val keysetAssociatedData = "labKey".toByteArray()

class RepositoryImpl(
    private val serializeKeysetByKeyService: SerializeKeysetByKeyService,
    private val sha256Service: Sha256Service,
    private val hexService: HexService
) : Repository {
    override fun createKey(
        keysetPassword: String,
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

    override fun getFileAeadList(): List<String> {
        return KeysetTemplates.Stream.entries
            .filter { it.name.endsWith("MB") }
            .map { it.name.removeSuffix("_1MB").lowercase() }
    }

    override fun getTextAeadList(): List<String> {
        return KeysetTemplates.AEAD.entries.map { it.name.lowercase() }
    }
}