package io.gromif.astracrypt.auth.domain.usecase.timeout

import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase

class CheckAuthTimeoutUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val clockService: ClockService
) {

    suspend operator fun invoke(): Boolean {
        val currentTime = clockService.currentTimeMillis()
        val lastActiveTime = clockService.getLastActiveTime()
        val timeout = getAuthUseCase().timeout
        val timeoutMillis = timeout.seconds * 1000

        return if (timeout == Timeout.NEVER) true else {
            currentTime - lastActiveTime < timeoutMillis
        }
    }

}