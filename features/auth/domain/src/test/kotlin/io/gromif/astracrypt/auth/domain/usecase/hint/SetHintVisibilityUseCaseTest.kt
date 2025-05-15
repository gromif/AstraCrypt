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

class SetHintVisibilityUseCaseTest {
    private lateinit var setHintVisibilityUseCase: SetHintVisibilityUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val setAuthUseCase: SetAuthUseCase = mockk()

    @Before
    fun setUp() {
        setHintVisibilityUseCase = SetHintVisibilityUseCase(getAuthUseCase, setAuthUseCase)
    }

    @Test
    fun shouldSetHintVisibility_thenUpdateAuthSettings() = runTest {
        val currentAuth = Auth()
        val targetHintVisibility = true
        val targetAuth = currentAuth.copy(hintState = targetHintVisibility)

        coEvery { getAuthUseCase() } returns currentAuth
        coJustRun { setAuthUseCase(targetAuth) }

        setHintVisibilityUseCase(targetHintVisibility)

        coVerify(exactly = 1) { getAuthUseCase() }
        coVerify(exactly = 1) { setAuthUseCase(targetAuth) }
    }
}
