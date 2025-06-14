package io.gromif.astracrypt.auth.presentation.shared.contract

import contract.auth.AuthContract
import io.gromif.astracrypt.auth.domain.usecase.SetLastActiveTimeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.CheckAuthTimeoutUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthContractImpl @Inject constructor(
    private val setLastActiveTimeUseCase: SetLastActiveTimeUseCase,
    private val checkAuthTimeoutUseCase: CheckAuthTimeoutUseCase,
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase
) : AuthContract {
    override suspend fun updateLastActiveTime() {
        setLastActiveTimeUseCase()
    }

    override suspend fun verifyTimeout() {
        checkAuthTimeoutUseCase()
    }

    override fun getAuthStateFlow(): Flow<Boolean> {
        return getAuthStateFlowUseCase().map {
            it.authState && it.skinState
        }
    }
}
