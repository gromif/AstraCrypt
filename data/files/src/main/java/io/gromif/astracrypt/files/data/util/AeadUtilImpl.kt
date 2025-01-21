package io.gromif.astracrypt.files.data.util

import com.google.crypto.tink.Aead
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aead
import io.gromif.astracrypt.files.domain.util.AeadUtil
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AeadUtilImpl(
    private val keysetManager: KeysetManager,
    private val base64Util: Base64Util
): AeadUtil {
    private val mutex = Mutex()

    override suspend fun decrypt(aeadIndex: Int, data: String): String {
        val encryptedBytes = base64Util.decode(data)
        val aead = getDecryptionAead(aeadIndex = aeadIndex)
        val decryptedBytes = aead.decrypt(encryptedBytes, keysetManager.associatedData)
        return decryptedBytes.decodeToString()
    }

    override suspend fun encrypt(aeadIndex: Int, data: String): String {
        val aead = getEncryptionAead(aeadIndex = aeadIndex)
        val encryptedBytes = aead.encrypt(data.toByteArray(), keysetManager.associatedData)
        return base64Util.encode(encryptedBytes)
    }

    private var cachedEncryptionAeadIndex: Int? = null
    private var cachedEncryptionAead: Aead? = null
    private suspend fun getEncryptionAead(aeadIndex: Int): Aead = mutex.withLock {
        val cachedAead = cachedEncryptionAead
        return if (cachedEncryptionAeadIndex == aeadIndex && cachedAead != null) cachedAead
        else getAead(aeadIndex = aeadIndex).also {
            cachedEncryptionAeadIndex = aeadIndex
            cachedEncryptionAead = it
        }
    }

    private var cachedDecryptionAeadIndex: Int? = null
    private var cachedDecryptionAead: Aead? = null
    private suspend fun getDecryptionAead(aeadIndex: Int): Aead = mutex.withLock {
        val cachedAead = cachedDecryptionAead
        return if (cachedDecryptionAeadIndex == aeadIndex && cachedAead != null) cachedAead
        else getAead(aeadIndex = aeadIndex).also {
            cachedDecryptionAeadIndex = aeadIndex
            cachedDecryptionAead = it
        }
    }

    private suspend fun getAead(aeadIndex: Int): Aead {
        return keysetManager.getKeyset(
            tag = "files_db",
            keyParams = KeysetTemplates.AEAD.entries[aeadIndex].params
        ).aead()
    }

}