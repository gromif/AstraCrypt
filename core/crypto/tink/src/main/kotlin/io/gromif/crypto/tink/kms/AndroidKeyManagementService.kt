package io.gromif.crypto.tink.kms

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.Parameters
import com.google.crypto.tink.integration.android.AndroidKeystore
import io.gromif.crypto.tink.keyset.KeysetIdUtil
import io.gromif.crypto.tink.keyset.io.KeysetReader
import io.gromif.crypto.tink.keyset.io.KeysetWriter
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithAead
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithAead

class AndroidKeyManagementService(
    private val keysetReader: KeysetReader,
    private val keysetWriter: KeysetWriter,
    private val keysetSerializerWithAead: KeysetSerializerWithAead,
    private val keysetParserWithAead: KeysetParserWithAead,
    private val prefsKeysetIdUtil: KeysetIdUtil,
    private val masterKeysetIdUtil: KeysetIdUtil,
) : KeyManagementService {

    override suspend fun create(
        tag: String,
        keyParams: Parameters,
        associatedData: ByteArray
    ): KeysetHandle {
        val prefsKey = prefsKeysetIdUtil.compute(
            tag = tag,
            associatedData = associatedData
        )
        val masterKey = masterKeysetIdUtil.compute(
            tag = tag,
            associatedData = associatedData
        )


        val savedKeyset = keysetReader.read(key = prefsKey)
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
            val serializedKeyset = keysetSerializerWithAead(
                keysetHandle = keysetHandle,
                aead = keysetAead,
                associatedData = newAssociatedData
            )
            keysetWriter.write(key = prefsKey, keyset = serializedKeyset)
            return keysetHandle
        } else {
            val keysetAead = AndroidKeystore.getAead(masterKey)
            return keysetParserWithAead(
                serializedKeyset = savedKeyset,
                aead = keysetAead,
                associatedData = newAssociatedData
            )
        }
    }

}