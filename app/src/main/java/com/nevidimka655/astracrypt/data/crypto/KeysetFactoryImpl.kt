package com.nevidimka655.astracrypt.data.crypto

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.Parameters
import com.google.crypto.tink.integration.android.AndroidKeystore
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.domain.usecase.crypto.MasterKeyNameUseCase
import com.nevidimka655.astracrypt.domain.usecase.crypto.PrefsKeyNameUseCase
import com.nevidimka655.crypto.tink.domain.model.keyset.KeysetFactory
import com.nevidimka655.crypto.tink.domain.usecase.ParseKeysetByAeadUseCase
import com.nevidimka655.crypto.tink.domain.usecase.SerializeKeysetByAeadUseCase

class KeysetFactoryImpl(
    private val keysetDataStoreManager: KeysetDataStoreManager,
    private val serializeKeysetByAeadUseCase: SerializeKeysetByAeadUseCase,
    private val parseKeysetByAeadUseCase: ParseKeysetByAeadUseCase,
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
            val serializedKeyset = serializeKeysetByAeadUseCase.serialize(
                keysetHandle = keysetHandle,
                aead = keysetAead,
                associatedData = newAssociatedData
            )
            keysetDataStoreManager.set(key = prefsKey, serializedKeyset = serializedKeyset)
            return keysetHandle
        } else {
            val keysetAead = AndroidKeystore.getAead(masterKey)
            return parseKeysetByAeadUseCase.parse(
                serializedKeyset = savedKeyset,
                aead = keysetAead,
                associatedData = newAssociatedData
            )
        }
    }

}