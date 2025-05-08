package io.gromif.astracrypt.auth.domain.usecase.auth.state

import io.gromif.astracrypt.auth.domain.model.AuthState
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAuthStateFlowUseCase(
    private val getAuthFlowUseCase: GetAuthFlowUseCase,
    private val repository: Repository
) {

    operator fun invoke(): Flow<AuthState> {
        return combine(
            flow = getAuthFlowUseCase(),
            flow2 = repository.getAuthStateFlow(),
            flow3 = repository.getSkinStateFlow()
        ) { config, authState, skinState ->
            AuthState(
                authType = config.type,
                authState = if(config.type == null) true else authState,
                skinType = config.skinType,
                skinState = if(config.skinType == null) true else skinState
            )
        }
    }

}