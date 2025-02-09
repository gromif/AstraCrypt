package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.service.TinkService

class DecryptTinkAdUseCase(
    private val tinkService: TinkService
) {

    suspend operator fun invoke(data: String) {
        tinkService.decryptAssociatedData(password = data)
    }

}