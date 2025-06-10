package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.service.AeadTextService

class ParseKeysetUseCase(
    private val aeadTextService: AeadTextService
) {

    suspend operator fun invoke(rawKeyset: String) {
        aeadTextService.setKeyset(rawKeyset)
    }

}