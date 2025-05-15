package io.gromif.astracrypt.auth.domain.usecase.timeout

import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase

class CheckAuthTimeoutUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val clockService: ClockService,
    private val repository: Repository
) {

    suspend operator fun invoke() {
        val currentTime = clockService.currentTimeMillis()
        val lastActiveTime = clockService.getLastActiveTime()
        val timeout = getAuthUseCase().timeout

        repository.verifyTimeout(
            currentTime = currentTime,
            lastActiveTime = lastActiveTime,
            timeout = timeout
        )
    }
}
