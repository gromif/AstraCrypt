package io.gromif.tink_lab.data.util

import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import io.gromif.crypto.tink.core.extensions.aead

class TextAeadUtil {
    private var targetKeysetHandle: KeysetHandle? = null
    private var aeadService: Aead? = null

    fun initKeyset(keysetHandle: KeysetHandle) {
        if (keysetHandle !== targetKeysetHandle) {
            targetKeysetHandle = keysetHandle
            aeadService = keysetHandle.aead()
        }
    }

    fun encryptBytes(associatedData: ByteArray, bytes: ByteArray): ByteArray {
        return aeadService!!.encrypt(bytes, associatedData)
    }

    fun decryptBytes(associatedData: ByteArray, bytes: ByteArray): ByteArray {
        return aeadService!!.decrypt(bytes, associatedData)
    }

}