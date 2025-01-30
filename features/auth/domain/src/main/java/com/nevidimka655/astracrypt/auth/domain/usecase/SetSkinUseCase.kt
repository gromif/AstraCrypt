package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.model.SkinType
import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository

class SetSkinUseCase(
    private val repository: Repository,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(auth: Auth, skinType: SkinType?, data: String?) {
        val skinHash = data?.let { tinkRepository.computeSkinHash(data = it) }
        repository.setSkinHash(hash = skinHash)
        repository.setAuth(auth = auth.copy(skinType = skinType))
    }

}