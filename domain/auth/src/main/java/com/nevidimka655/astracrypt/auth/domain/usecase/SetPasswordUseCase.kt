package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository

class SetPasswordUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(auth: Auth, password: String?) {
        repository.setPassword(auth = auth, password = password)
    }

}