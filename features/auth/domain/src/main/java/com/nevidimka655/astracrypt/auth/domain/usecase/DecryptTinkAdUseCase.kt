package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.service.TinkService

class DecryptTinkAdUseCase(
    private val tinkService: TinkService
) {

    suspend operator fun invoke(data: String) {
        tinkService.decryptAssociatedData(password = data)
    }

}