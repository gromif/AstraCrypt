package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.service.ClockService

class SetLastActiveTimeUseCase(
    private val clockService: ClockService
) {

    operator fun invoke() {
        val currentTimeMillis = clockService.currentTimeMillis()
        clockService.setLastActiveTime(currentTimeMillis)
    }

}
