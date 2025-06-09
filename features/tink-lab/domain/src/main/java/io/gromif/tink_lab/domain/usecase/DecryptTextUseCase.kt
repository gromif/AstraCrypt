package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.service.AeadTextService

class DecryptTextUseCase(
    private val aeadTextService: AeadTextService,
) {

    suspend operator fun invoke(encryptedText: String, associatedData: String): EncryptionResult {
        return aeadTextService.decryptText(
            encryptedText = encryptedText,
            associatedData = associatedData
        )
    }

}