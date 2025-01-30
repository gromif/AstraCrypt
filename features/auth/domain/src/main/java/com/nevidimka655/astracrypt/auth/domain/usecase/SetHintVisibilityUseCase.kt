package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.Repository

class SetHintVisibilityUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(auth: Auth, visible: Boolean) {
        repository.setHintVisibility(auth = auth, visible = visible)
    }

}