package io.gromif.astracrypt.auth.domain.usecase.encryption

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetBindTinkAdUseCaseTest {
    private lateinit var setBindTinkAdUseCase: SetBindTinkAdUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val setAuthUseCase: SetAuthUseCase = mockk()
    private val tinkService: TinkService = mockk()

    @Before
    fun setUp() {
        setBindTinkAdUseCase = SetBindTinkAdUseCase(getAuthUseCase, setAuthUseCase, tinkService)
    }

    @Test
    fun shouldUpdateAuthSettings_thenSetCorrectBindState() = runTest {
        val currentAuth = Auth()
        val targetBindState = true
        val targetPassword = "password"
        val targetAuth = currentAuth.copy(bindTinkAd = targetBindState)

        coEvery { getAuthUseCase() } returns currentAuth
        coJustRun { setAuthUseCase(targetAuth) }
        coJustRun { tinkService.enableAssociatedDataBind(targetPassword) }

        setBindTinkAdUseCase(targetBindState, targetPassword)

        coVerify(exactly = 1) { getAuthUseCase() }
        coVerify(exactly = 1) { setAuthUseCase(targetAuth) }
        coVerify(exactly = 1) { tinkService.enableAssociatedDataBind(targetPassword) }

        confirmVerified(tinkService, setAuthUseCase, getAuthUseCase)
    }
}
