package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.EncryptionException
import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.model.Repository

class DecryptTextUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(encryptedText: String, associatedData: String): EncryptionResult {
        if (!repository.isKeysetLoaded())
            return EncryptionResult.Error(EncryptionException.NoValidKeyset)

        return repository.decryptText(
            encryptedText = encryptedText,
            associatedData = associatedData
        )
    }

}