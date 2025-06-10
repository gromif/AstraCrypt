package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.service.AeadTextService

class EncryptTextUseCase(
    private val aeadTextService: AeadTextService,
) {

    suspend operator fun invoke(text: String, associatedData: String): EncryptionResult {
        return aeadTextService.encryptText(
            text = text,
            associatedData = associatedData
        )
    }

}