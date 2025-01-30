package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.model.AuthType
import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository

class SetAuthUseCase(
    private val repository: Repository,
    private val tinkRepository: TinkRepository,
) {

    suspend operator fun invoke(auth: Auth, authType: AuthType?, data: String?) {
        val authHash = data?.let { tinkRepository.computeAuthHash(data = it) }
        repository.setAuthHash(hash = authHash)
        repository.setAuth(
            auth = auth.copy(
                type = authType,
                bindTinkAd = authType?.let { auth.bindTinkAd } ?: false
            )
        )
        if (authType == null) tinkRepository.disableAssociatedDataBind()
    }

}