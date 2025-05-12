package io.gromif.tink_lab.domain.model

sealed class EncryptionResult {

    data class Success(val text: String): EncryptionResult()

    data class Error(val encryptionException: EncryptionException): EncryptionResult()

}