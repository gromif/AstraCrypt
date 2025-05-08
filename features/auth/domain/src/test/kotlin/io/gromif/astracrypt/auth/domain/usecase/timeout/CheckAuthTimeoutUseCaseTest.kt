package io.gromif.astracrypt.auth.domain.usecase.timeout

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CheckAuthTimeoutUseCaseTest {
    private lateinit var checkAuthTimeoutUseCase: CheckAuthTimeoutUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val clockService: ClockService = mockk()
    private val repositoryMock: Repository = mockk()

    @Before
    fun setUp() {
        checkAuthTimeoutUseCase = CheckAuthTimeoutUseCase(getAuthUseCase, clockService, repositoryMock)
    }

    @Test
    fun `should correctly validate the auth timeout`() = runTest {
        val currentTime = System.currentTimeMillis()
        val lastActiveTime = currentTime - Timeout.SECONDS_10.seconds * 1000
        val targetTimeout = Timeout.SECONDS_15
        val targetAuth = Auth(timeout = targetTimeout)

        coEvery { getAuthUseCase() } returns targetAuth
        every { clockService.currentTimeMillis() } returns currentTime
        every { clockService.getLastActiveTime() } returns lastActiveTime
        justRun { repositoryMock.verifyTimeout(currentTime, lastActiveTime, targetTimeout) }

        checkAuthTimeoutUseCase()

        coVerify(exactly = 1) { getAuthUseCase() }
        verify(exactly = 1) { clockService.currentTimeMillis() }
        verify(exactly = 1) { clockService.getLastActiveTime() }
        verify(exactly = 1) { repositoryMock.verifyTimeout(currentTime, lastActiveTime, targetTimeout) }
    }
}