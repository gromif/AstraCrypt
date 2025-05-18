package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.model.ValidationRulesDto
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Before
import org.junit.Test

class OpenNavFolderUseCaseTest {
    private lateinit var openNavFolderUseCase: OpenNavFolderUseCase<Unit>
    private val storageNavigatorMock: StorageNavigator = mockk(relaxed = true)
    private val getCurrentNavFolderUseCaseMock: GetCurrentNavFolderUseCase = mockk()
    private val invalidateDataSourceUseCaseMock: InvalidateDataSourceUseCase<Unit> =
        mockk(relaxed = true)
    private val getValidationRulesUseCaseMock: GetValidationRulesUseCase = mockk()

    @Before
    fun setUp() {
        every { getValidationRulesUseCaseMock() } returns ValidationRulesDto(
            minNameLength = 3,
            maxNameLength = 8,
            maxBackstackNameLength = 8
        )

        openNavFolderUseCase = OpenNavFolderUseCase(
            storageNavigator = storageNavigatorMock,
            getCurrentNavFolderUseCase = getCurrentNavFolderUseCaseMock,
            invalidateDataSourceUseCase = invalidateDataSourceUseCaseMock,
            getValidationRulesUseCase = getValidationRulesUseCaseMock
        )
    }

    @Test
    fun `should do nothing when the target ID equals the current ID`() {
        val currentId = 4L
        val targetId = currentId
        val targetName = ""

        every {
            getCurrentNavFolderUseCaseMock()
        } returns StorageNavigator.Folder(currentId, targetName)

        openNavFolderUseCase(targetId, targetName)

        verify(exactly = 1) { getCurrentNavFolderUseCaseMock() }

        tearDown()
    }

    @Test
    fun `should correctly update the backStack and the dataSource when the name exceeds allowed length`() {
        val currentId = 4L
        val targetId = 5L
        val targetName = "qwertyuiop"

        every {
            getCurrentNavFolderUseCaseMock()
        } returns StorageNavigator.Folder(currentId, targetName)

        openNavFolderUseCase(targetId, targetName)

        val expectedName = "qwertyui…"
        val expectedFolder = StorageNavigator.Folder(targetId, expectedName)
        verifyOrder {
            getCurrentNavFolderUseCaseMock()
            getValidationRulesUseCaseMock()
            storageNavigatorMock.push(expectedFolder)
            invalidateDataSourceUseCaseMock()
        }
    }

    @After
    fun tearDown() {
        confirmVerified(
            storageNavigatorMock,
            getCurrentNavFolderUseCaseMock,
            invalidateDataSourceUseCaseMock,
            getValidationRulesUseCaseMock
        )
    }
}