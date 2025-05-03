package io.gromif.astracrypt.files.data.util

import com.google.crypto.tink.Aead
import io.gromif.crypto.tink.aead.AeadManager
import io.gromif.crypto.tink.aead.decodeAndDecrypt
import io.gromif.crypto.tink.aead.encryptAndEncode
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager

class AeadUtil(
    private val aeadManager: AeadManager,
    private val associatedDataManager: AssociatedDataManager,
    private val base64Encoder: Base64Encoder,
) {
    private var cachedAeadMap: HashMap<Int, Aead> = hashMapOf()

    suspend fun decrypt(aeadIndex: Int, data: String): String {
        val aead = getAead(aeadIndex = aeadIndex)
        return aead.decodeAndDecrypt(
            value = data,
            associatedData = associatedDataManager.getAssociatedData(),
            encoder = base64Encoder
        )
    }

    suspend fun encrypt(aeadIndex: Int, data: String): String {
        val aead = getAead(aeadIndex = aeadIndex)
        return aead.encryptAndEncode(
            value = data,
            associatedData = associatedDataManager.getAssociatedData(),
            encoder = base64Encoder
        )
    }

    private suspend fun getAead(aeadIndex: Int): Aead {
        val currentCachedAead = cachedAeadMap[aeadIndex]

        return currentCachedAead ?: aeadManager.aead(
            tag = TAG_KEYSET,
            keyParams = KeysetTemplates.AEAD.entries[aeadIndex].params
        ).also {
            cachedAeadMap[aeadIndex] = it
        }
    }

}

private const val TAG_KEYSET = "files_db"