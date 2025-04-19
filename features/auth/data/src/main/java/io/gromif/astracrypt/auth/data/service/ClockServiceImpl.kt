package io.gromif.astracrypt.auth.data.service

import io.gromif.astracrypt.auth.domain.service.ClockService

class ClockServiceImpl: ClockService {
    private var lastActiveTime: Long = -1

    override fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    override fun setLastActiveTime(millis: Long) {
        lastActiveTime = millis
    }

    override fun getLastActiveTime(): Long {
        return lastActiveTime
    }
}