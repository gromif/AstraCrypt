package io.gromif.crypto.tink.kms

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.Parameters

/**
 * Defines the process for [com.google.crypto.tink.KeysetHandle] creation.
 */
interface KeyManagementService {

    suspend fun create(
        tag: String,
        keyParams: Parameters,
        associatedData: ByteArray
    ): KeysetHandle

}