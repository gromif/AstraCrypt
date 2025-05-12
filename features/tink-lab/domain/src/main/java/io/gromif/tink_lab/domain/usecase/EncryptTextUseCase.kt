package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.EncryptionException
import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.model.Repository

class EncryptTextUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(text: String, associatedData: String): EncryptionResult {
        if (!repository.isKeysetLoaded())
            return EncryptionResult.Error(EncryptionException.NoValidKeyset)

        return repository.encryptText(
            text = text,
            associatedData = associatedData
        )
    }

}