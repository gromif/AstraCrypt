package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetAuthFlowUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Auth> {
        return repository.getAuthFlow()
    }

}