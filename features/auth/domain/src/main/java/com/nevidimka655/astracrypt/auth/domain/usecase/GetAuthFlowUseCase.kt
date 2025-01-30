package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetAuthFlowUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Auth> {
        return repository.getAuthFlow()
    }

}