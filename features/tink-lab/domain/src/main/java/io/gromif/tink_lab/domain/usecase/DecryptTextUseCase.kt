package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.model.Repository

class DecryptTextUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(encryptedText: String, associatedData: String): EncryptionResult {
        return repository.decryptText(
            encryptedText = encryptedText,
            associatedData = associatedData
        )
    }

}