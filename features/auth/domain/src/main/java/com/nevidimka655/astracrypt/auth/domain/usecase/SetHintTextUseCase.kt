package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.Repository

class SetHintTextUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(auth: Auth, text: String) {
        repository.setHintText(auth = auth, text = text)
    }

}