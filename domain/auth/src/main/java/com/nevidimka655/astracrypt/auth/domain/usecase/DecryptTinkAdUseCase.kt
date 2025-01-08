package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.TinkRepository

class DecryptTinkAdUseCase(
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(data: String) {
        tinkRepository.decryptAssociatedData(password = data)
    }

}