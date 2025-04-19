package io.gromif.astracrypt.auth.domain.service

interface ClockService {

    fun currentTimeMillis(): Long

    fun setLastActiveTime(millis: Long)
    fun getLastActiveTime(): Long

}