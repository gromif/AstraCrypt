package io.gromif.astracrypt.auth.domain.usecase.timeout

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.usecase.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetAuthUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetTimeoutUseCaseTest {
    private lateinit var setTimeoutUseCase: SetTimeoutUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val setAuthUseCase: SetAuthUseCase = mockk()

    @Before
    fun setUp() {
        setTimeoutUseCase = SetTimeoutUseCase(getAuthUseCase, setAuthUseCase)
    }

    @Test
    fun `should update Auth with the correct timeout value`() = runTest {
        val currentAuth = Auth()
        val targetTimeout = Timeout.NEVER
        val targetAuth = currentAuth.copy(timeout = targetTimeout)

        coEvery { getAuthUseCase() } returns currentAuth
        coJustRun { setAuthUseCase(targetAuth) }

        setTimeoutUseCase(targetTimeout)

        coVerify(exactly = 1) { getAuthUseCase() }
        coVerify(exactly = 1) { setAuthUseCase(targetAuth) }
    }
}