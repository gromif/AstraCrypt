package io.gromif.astracrypt.files.data.util

import com.google.crypto.tink.Aead
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.extensions.aead
import io.gromif.crypto.tink.model.KeysetTemplates

class AeadUtil(
    private val keysetManager: KeysetManager,
    private val associatedDataManager: AssociatedDataManager,
    private val base64Encoder: Base64Encoder
) {
    private var cachedAead: Pair<Int, Aead>? = null

    suspend fun decrypt(aeadIndex: Int, data: String): String {
        val encryptedBytes = base64Encoder.decode(data)
        val aead = getAead(aeadIndex = aeadIndex)
        val decryptedBytes = aead.decrypt(
            /* ciphertext = */ encryptedBytes,
            /* associatedData = */ associatedDataManager.getAssociatedData()
        )
        return decryptedBytes.decodeToString()
    }

    suspend fun encrypt(aeadIndex: Int, data: String): String {
        val aead = getAead(aeadIndex = aeadIndex)
        val encryptedBytes = aead.encrypt(
            /* plaintext = */ data.toByteArray(),
            /* associatedData = */ associatedDataManager.getAssociatedData()
        )
        return base64Encoder.encode(encryptedBytes)
    }

    private suspend fun getAead(aeadIndex: Int): Aead {
        val currentCachedAead = cachedAead

        return if (currentCachedAead != null && currentCachedAead.first == aeadIndex) {
            currentCachedAead.second
        } else keysetManager.getKeyset(
            tag = TAG_KEYSET,
            keyParams = KeysetTemplates.AEAD.entries[aeadIndex].params
        ).aead().also {
            cachedAead = Pair(aeadIndex, it)
        }
    }

}

private const val TAG_KEYSET = "files_db"