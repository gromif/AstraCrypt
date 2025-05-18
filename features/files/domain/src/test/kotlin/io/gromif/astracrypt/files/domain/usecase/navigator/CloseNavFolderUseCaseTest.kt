package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Before
import org.junit.Test

private typealias DUMMY = Unit

class CloseNavFolderUseCaseTest {
    private lateinit var closeNavFolderUseCase: CloseNavFolderUseCase<DUMMY>
    private val storageNavigatorMock: StorageNavigator = mockk(relaxed = true)
    private val invalidateDataSourceUseCaseMock: InvalidateDataSourceUseCase<DUMMY> =
        mockk(relaxed = true)

    @Before
    fun setUp() {
        closeNavFolderUseCase = CloseNavFolderUseCase(
            storageNavigator = storageNavigatorMock,
            invalidateDataSourceUseCase = invalidateDataSourceUseCaseMock
        )
    }

    @Test
    fun `should close the current folder and invalidate the data source`() {
        closeNavFolderUseCase()

        verifyOrder {
            storageNavigatorMock.pop()
            invalidateDataSourceUseCaseMock()
        }
    }

    @After
    fun tearDown() {
        confirmVerified(storageNavigatorMock, invalidateDataSourceUseCaseMock)
    }

}