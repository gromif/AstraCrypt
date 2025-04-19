package io.gromif.astracrypt.auth.domain.usecase.hint

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetHintTextUseCaseTest {
    private lateinit var setHintTextUseCase: SetHintTextUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val setAuthUseCase: SetAuthUseCase = mockk()

    @Before
    fun setUp() {
        setHintTextUseCase = SetHintTextUseCase(getAuthUseCase, setAuthUseCase)
    }

    @Test
    fun shouldSetHintText_thenUpdateAuthSettings() = runTest {
        val currentAuth = Auth()
        val targetHintText = "hint"
        val targetAuth = currentAuth.copy(hintText = targetHintText)

        coEvery { getAuthUseCase() } returns currentAuth
        coJustRun { setAuthUseCase(targetAuth) }

        setHintTextUseCase(targetHintText)

        coVerify(exactly = 1) { getAuthUseCase() }
        coVerify(exactly = 1) { setAuthUseCase(targetAuth) }
    }
}