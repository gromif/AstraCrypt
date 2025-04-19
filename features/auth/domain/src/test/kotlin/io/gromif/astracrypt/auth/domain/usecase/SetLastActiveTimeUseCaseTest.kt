package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.service.ClockService
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetLastActiveTimeUseCaseTest {
    private lateinit var setLastActiveTimeUseCase: SetLastActiveTimeUseCase
    private val clockService: ClockService = mockk()

    @Before
    fun setUp() {
        setLastActiveTimeUseCase = SetLastActiveTimeUseCase(clockService)
    }

    @Test
    fun `should properly save current time`() = runTest {
        val currentTime = System.currentTimeMillis()

        every { clockService.currentTimeMillis() } returns currentTime
        justRun { clockService.setLastActiveTime(currentTime) }

        setLastActiveTimeUseCase()

        verify(exactly = 1) { clockService.currentTimeMillis() }
        verify(exactly = 1) { clockService.setLastActiveTime(currentTime) }
    }
}