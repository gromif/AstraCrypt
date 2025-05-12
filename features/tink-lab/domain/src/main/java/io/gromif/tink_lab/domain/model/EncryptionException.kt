package io.gromif.tink_lab.domain.model

sealed class EncryptionException(
    message: String
): Exception(message) {

    data object NoValidKeyset: EncryptionException(message = "No valid keyset instance found!") {
        private fun readResolve(): Any = NoValidKeyset
    }

    data class EncryptionFailed(
        val exception: Exception
    ): EncryptionException(message = "Encryption failed!")

    data class DecryptionFailed(
        val exception: Exception
    ): EncryptionException(message = "Decryption failed!")

}