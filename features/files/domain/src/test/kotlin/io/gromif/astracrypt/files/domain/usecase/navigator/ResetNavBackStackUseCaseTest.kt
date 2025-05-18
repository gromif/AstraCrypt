package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResetNavBackStackUseCaseTest {
    private lateinit var resetNavBackStackUseCase: ResetNavBackStackUseCase<Unit>
    private val swapNavBackStackUseCase: SwapNavBackStackUseCase<Unit> = mockk(relaxed = true)

    @Before
    fun setUp() {
        resetNavBackStackUseCase = ResetNavBackStackUseCase(
            swapNavBackStackUseCase = swapNavBackStackUseCase
        )
    }

    @Test
    fun `should call swapNavBackStackUseCase with correct parameters`() {
        val targetBackStack = emptyList<StorageNavigator.Folder>()

        resetNavBackStackUseCase()

        verify(exactly = 1) { swapNavBackStackUseCase(targetBackStack) }
    }

    @After
    fun tearDown() {
        confirmVerified(swapNavBackStackUseCase)
    }
}