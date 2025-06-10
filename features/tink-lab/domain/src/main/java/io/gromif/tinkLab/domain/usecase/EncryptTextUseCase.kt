package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.EncryptionResult
import io.gromif.tinkLab.domain.service.AeadTextService

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
