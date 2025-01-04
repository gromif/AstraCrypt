package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository

class SetBindTinkAdUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(auth: Auth, bind: Boolean) {
        repository.setBindTinkAd(auth = auth, bind = bind)
    }

}