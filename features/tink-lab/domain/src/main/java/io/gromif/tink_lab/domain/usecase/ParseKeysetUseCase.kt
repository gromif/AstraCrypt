package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.service.AeadTextService

class ParseKeysetUseCase(
    private val aeadTextService: AeadTextService
) {

    suspend operator fun invoke(rawKeyset: String) {
        aeadTextService.setKeyset(rawKeyset)
    }

}