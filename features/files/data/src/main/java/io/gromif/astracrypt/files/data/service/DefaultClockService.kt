package io.gromif.astracrypt.files.data.service

import io.gromif.astracrypt.files.domain.service.ClockService

class DefaultClockService: ClockService {

    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }

}
