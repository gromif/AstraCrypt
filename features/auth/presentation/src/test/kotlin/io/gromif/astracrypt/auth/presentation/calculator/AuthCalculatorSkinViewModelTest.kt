package io.gromif.astracrypt.auth.presentation.calculator

import io.gromif.astracrypt.auth.domain.usecase.skin.VerifySkinUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthCalculatorSkinViewModelTest {
    private lateinit var vm: AuthCalculatorSkinViewModel
    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val verifySkinUseCaseMock: VerifySkinUseCase = mockk()

    @Before
    fun setUp() {
        vm = AuthCalculatorSkinViewModel(
            defaultDispatcher = defaultDispatcher,
            verifySkinUseCase = verifySkinUseCaseMock
        )
    }

    @Test
    fun `verifySkin calls verifySkinUseCase correctly`() = runTest {
        val targetData = "12345"
        val targetResult = false

        coEvery { verifySkinUseCaseMock(targetData) } returns targetResult

        vm.verifySkin(targetData)

        coVerify(exactly = 1) { verifySkinUseCaseMock(targetData) }
    }
}
