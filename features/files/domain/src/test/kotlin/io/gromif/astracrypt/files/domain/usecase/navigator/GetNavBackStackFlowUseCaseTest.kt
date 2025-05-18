package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private typealias Folder = StorageNavigator.Folder

class GetNavBackStackFlowUseCaseTest {
    private lateinit var getNavBackStackFlowUseCase: GetNavBackStackFlowUseCase
    private val storageNavigatorMock: StorageNavigator = mockk()

    @Before
    fun setUp() {
        getNavBackStackFlowUseCase = GetNavBackStackFlowUseCase(
            storageNavigator = storageNavigatorMock
        )
    }

    @Test
    fun `should correctly return the backStack flow`() {
        val targetBackStackFlow = flowOf(emptyList<Folder>())

        every { storageNavigatorMock.getBackStackFlow() } returns targetBackStackFlow

        val result = getNavBackStackFlowUseCase()

        Assert.assertSame(targetBackStackFlow, result)
        verify(exactly = 1) { storageNavigatorMock.getBackStackFlow() }
    }

    @After
    fun tearDown() {
        confirmVerified(storageNavigatorMock)
    }
}