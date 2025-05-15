package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase
import kotlinx.coroutines.flow.first

class SetLastActiveTimeUseCase(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val clockService: ClockService
) {

    suspend operator fun invoke() {
        val currentState = getAuthStateFlowUseCase().first()

        if (currentState.authState && currentState.skinState) {
            val currentTimeMillis = clockService.currentTimeMillis()
            clockService.setLastActiveTime(currentTimeMillis)
        }
    }
}
