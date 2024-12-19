package com.nevidimka655.astracrypt.data.crypto

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.Parameters
import com.google.crypto.tink.integration.android.AndroidKeystore
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.domain.usecase.crypto.MasterKeyNameUseCase
import com.nevidimka655.astracrypt.domain.usecase.crypto.PrefsKeyNameUseCase
import com.nevidimka655.crypto.tink.data.parsers.ParseKeysetByAeadService
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByAeadService
import com.nevidimka655.crypto.tink.domain.keyset.KeysetFactory

class KeysetFactoryImpl(
    private val keysetDataStoreManager: KeysetDataStoreManager,
    private val serializeKeysetByAeadService: SerializeKeysetByAeadService,
    private val parseKeysetByAeadService: ParseKeysetByAeadService,
    private val prefsKeyNameUseCase: PrefsKeyNameUseCase,
    private val masterKeyNameUseCase: MasterKeyNameUseCase
) : KeysetFactory {

    override suspend fun create(
        tag: String,
        keyParams: Parameters,
        associatedData: ByteArray
    ): KeysetHandle {
        val prefsKey = prefsKeyNameUseCase.get(
            tag = tag,
            associatedData = associatedData
        )
        val masterKey = masterKeyNameUseCase.get(
            tag = tag,
            associatedData = associatedData
        )


        val savedKeyset = keysetDataStoreManager.get(key = prefsKey)
        val isMasterKeyExists = AndroidKeystore.hasKey(masterKey)
        if (savedKeyset != null && !isMasterKeyExists) {
            throw IllegalStateException(
                "There exists an encrypted keyset, but the key to decrypt it is missing."
            )
        }

        val newAssociatedData = byteArrayOf(*tag.toByteArray(), *associatedData)
        if (savedKeyset == null) {
            AndroidKeystore.generateNewAes256GcmKey(masterKey)
            val keysetAead = AndroidKeystore.getAead(masterKey)

            val keysetHandle = KeysetHandle.generateNew(keyParams)
            val serializedKeyset = serializeKeysetByAeadService.serialize(
                keysetHandle = keysetHandle,
                aead = keysetAead,
                associatedData = newAssociatedData
            )
            keysetDataStoreManager.set(key = prefsKey, serializedKeyset = serializedKeyset)
            return keysetHandle
        } else {
            val keysetAead = AndroidKeystore.getAead(masterKey)
            return parseKeysetByAeadService.parse(
                serializedKeyset = savedKeyset,
                aead = keysetAead,
                associatedData = newAssociatedData
            )
        }
    }

}