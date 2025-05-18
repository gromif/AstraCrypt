package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Before
import org.junit.Test

class SwapNavBackStackUseCaseTest {
    private lateinit var swapNavBackStackUseCase: SwapNavBackStackUseCase<Unit>
    private val storageNavigatorMock: StorageNavigator = mockk(relaxed = true)
    private val invalidateDataSourceUseCaseMock: InvalidateDataSourceUseCase<Unit> =
        mockk(relaxed = true)

    @Before
    fun setUp() {
        swapNavBackStackUseCase = SwapNavBackStackUseCase(
            storageNavigator = storageNavigatorMock,
            invalidateDataSourceUseCase = invalidateDataSourceUseCaseMock
        )
    }

    @Test
    fun `should correctly swap the backStack with a new one and invalidate the data source`() {
        val targetBackStack = emptyList<StorageNavigator.Folder>()

        swapNavBackStackUseCase(targetBackStack)

        verifyOrder {
            storageNavigatorMock.swapBackStackWith(targetBackStack)
            invalidateDataSourceUseCaseMock()
        }
    }

    @After
    fun tearDown() {
        confirmVerified(storageNavigatorMock, invalidateDataSourceUseCaseMock)
    }
}