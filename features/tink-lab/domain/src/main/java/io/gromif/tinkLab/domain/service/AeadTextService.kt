package io.gromif.tinkLab.domain.service

import io.gromif.tinkLab.domain.model.EncryptionResult

interface AeadTextService {

    suspend fun setKeyset(rawKeyset: String)

    suspend fun encryptText(text: String, associatedData: String): EncryptionResult

    suspend fun decryptText(encryptedText: String, associatedData: String): EncryptionResult
}
