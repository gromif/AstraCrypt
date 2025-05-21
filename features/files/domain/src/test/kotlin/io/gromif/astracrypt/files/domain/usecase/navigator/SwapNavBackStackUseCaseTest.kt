package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Before
import org.junit.Test

class SwapNavBackStackUseCaseTest {
    private lateinit var swapNavBackStackUseCase: SwapNavBackStackUseCase
    private val storageNavigatorMock: StorageNavigator = mockk(relaxed = true)

    @Before
    fun setUp() {
        swapNavBackStackUseCase = SwapNavBackStackUseCase(storageNavigator = storageNavigatorMock)
    }

    @Test
    fun `should correctly swap the backStack with a new one`() {
        val targetBackStack = emptyList<StorageNavigator.Folder>()

        swapNavBackStackUseCase(targetBackStack)

        verifyOrder {
            storageNavigatorMock.swapBackStackWith(targetBackStack)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(storageNavigatorMock)
    }
}
