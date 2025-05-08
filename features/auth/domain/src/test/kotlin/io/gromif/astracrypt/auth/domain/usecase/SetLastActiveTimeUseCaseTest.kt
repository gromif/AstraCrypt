package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.model.AuthState
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SetLastActiveTimeUseCaseTest {
    private lateinit var setLastActiveTimeUseCase: SetLastActiveTimeUseCase
    private val getAuthStateFlowUseCaseMock: GetAuthStateFlowUseCase = mockk()
    private val clockService: ClockService = mockk()

    @Before
    fun setUp() {
        setLastActiveTimeUseCase = SetLastActiveTimeUseCase(getAuthStateFlowUseCaseMock, clockService)
    }

    @Test
    fun `shouldn't save the current time when the auth is invalid`() = runTest {
        val targetAuthState = AuthState(
            authType = AuthType.PASSWORD,
            authState = false,
            skinType = SkinType.Calculator,
            skinState = true
        )

        every { getAuthStateFlowUseCaseMock() } returns flowOf(targetAuthState)

        setLastActiveTimeUseCase()

        verify(exactly = 1) { getAuthStateFlowUseCaseMock() }
        verify(exactly = 0) { clockService.currentTimeMillis() }
        verify(exactly = 0) { clockService.setLastActiveTime(any()) }
    }

    @Test
    fun `shouldn't save the current time when the skin is invalid`() = runTest {
        val targetAuthState = AuthState(
            authType = AuthType.PASSWORD,
            authState = true,
            skinType = SkinType.Calculator,
            skinState = false
        )

        every { getAuthStateFlowUseCaseMock() } returns flowOf(targetAuthState)

        setLastActiveTimeUseCase()

        verify(exactly = 1) { getAuthStateFlowUseCaseMock() }
        verify(exactly = 0) { clockService.currentTimeMillis() }
        verify(exactly = 0) { clockService.setLastActiveTime(any()) }
    }

    @Test
    fun `should save the current time when both the Auth and the Skin are valid`() = runTest {
        val targetAuthState = AuthState(
            authType = AuthType.PASSWORD,
            authState = true,
            skinType = SkinType.Calculator,
            skinState = true
        )
        val currentTime = System.currentTimeMillis()

        every { getAuthStateFlowUseCaseMock() } returns flowOf(targetAuthState)
        every { clockService.currentTimeMillis() } returns currentTime
        justRun { clockService.setLastActiveTime(currentTime) }

        setLastActiveTimeUseCase()

        verify(exactly = 1) { getAuthStateFlowUseCaseMock() }
        verify(exactly = 1) { clockService.currentTimeMillis() }
        verify(exactly = 1) { clockService.setLastActiveTime(currentTime) }
    }

    @After
    fun tearDown() {
        confirmVerified(getAuthStateFlowUseCaseMock, clockService)
    }
}