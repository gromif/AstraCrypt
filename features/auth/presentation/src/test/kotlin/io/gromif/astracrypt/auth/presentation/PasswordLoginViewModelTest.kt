package io.gromif.astracrypt.auth.presentation

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.usecase.DecryptTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PasswordLoginViewModelTest {
    private lateinit var passwordLoginViewModel: PasswordLoginViewModel
    private val verifyAuthUseCaseMock: VerifyAuthUseCase = mockk()
    private val decryptTinkAdUseCaseMock: DecryptTinkAdUseCase = mockk()
    private val getAuthFlowUseCaseMock: GetAuthFlowUseCase = mockk()
    private val targetAuthFlow = flow<Auth> { Auth() }

    @Before
    fun setUp() {
        every { getAuthFlowUseCaseMock() } returns targetAuthFlow

        passwordLoginViewModel = PasswordLoginViewModel(
            verifyAuthUseCaseMock, decryptTinkAdUseCaseMock, getAuthFlowUseCaseMock
        )
    }

    @Test
    fun `should start listening for auth changes`() {
        verify(exactly = 1) { getAuthFlowUseCaseMock() }
    }

    @Test
    fun `should call verifyAuthUseCase when verifying the password`() = runTest {
        val targetPassword = "password"

        coEvery { verifyAuthUseCaseMock(targetPassword) } returns true
        passwordLoginViewModel.verifyPassword(targetPassword)

        coVerify { verifyAuthUseCaseMock(targetPassword) }
    }

    @Test
    fun `should return the correct bindTinkAd value`() {
        val result = passwordLoginViewModel.isTinkAdTiedToAuth()

        Assert.assertFalse(result)
    }

    @Test
    fun `should call decryptTinkAdUseCase when decrypting global associated data`() = runTest {
        val targetPassword = "password"

        coJustRun { decryptTinkAdUseCaseMock(targetPassword) }
        passwordLoginViewModel.decryptTinkAd(targetPassword)

        coVerify { decryptTinkAdUseCaseMock(targetPassword) }
    }

}