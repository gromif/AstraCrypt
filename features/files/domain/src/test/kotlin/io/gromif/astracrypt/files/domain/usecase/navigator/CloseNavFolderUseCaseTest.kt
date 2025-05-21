package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Before
import org.junit.Test

class CloseNavFolderUseCaseTest {
    private lateinit var closeNavFolderUseCase: CloseNavFolderUseCase
    private val storageNavigatorMock: StorageNavigator = mockk(relaxed = true)

    @Before
    fun setUp() {
        closeNavFolderUseCase = CloseNavFolderUseCase(storageNavigator = storageNavigatorMock)
    }

    @Test
    fun `should correctly close the current folder`() {
        closeNavFolderUseCase()

        verifyOrder {
            storageNavigatorMock.pop()
        }
    }

    @After
    fun tearDown() {
        confirmVerified(storageNavigatorMock)
    }
}
