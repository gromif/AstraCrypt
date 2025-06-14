package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.EncryptionResult
import io.gromif.tinkLab.domain.service.AeadTextService

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
