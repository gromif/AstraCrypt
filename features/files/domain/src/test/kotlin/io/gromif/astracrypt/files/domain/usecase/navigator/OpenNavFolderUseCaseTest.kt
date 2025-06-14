package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.model.ValidationRulesDto
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class OpenNavFolderUseCaseTest {
    private lateinit var openNavFolderUseCase: OpenNavFolderUseCase
    private val storageNavigatorMock: StorageNavigator = mockk(relaxed = true)
    private val getCurrentNavFolderFlowUseCaseMock: GetCurrentNavFolderFlowUseCase = mockk()
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
            getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCaseMock,
            getValidationRulesUseCase = getValidationRulesUseCaseMock
        )
    }

    @Test
    fun `should do nothing when the target ID equals the current ID`() = runTest {
        val currentId = 4L
        val targetId = currentId
        val targetName = ""

        every {
            getCurrentNavFolderFlowUseCaseMock()
        } returns flowOf(StorageNavigator.Folder(currentId, targetName))

        openNavFolderUseCase(targetId, targetName)

        verify(exactly = 1) { getCurrentNavFolderFlowUseCaseMock() }

        tearDown()
    }

    @Test
    fun `correctly updates the backStack-dataSource when the name exceeds max length`() = runTest {
        val currentId = 4L
        val targetId = 5L
        val targetName = "qwertyuiop"

        every {
            getCurrentNavFolderFlowUseCaseMock()
        } returns flowOf(StorageNavigator.Folder(currentId, targetName))

        openNavFolderUseCase(targetId, targetName)

        val expectedName = "qwertyui…"
        val expectedFolder = StorageNavigator.Folder(targetId, expectedName)
        verifyOrder {
            getCurrentNavFolderFlowUseCaseMock()
            getValidationRulesUseCaseMock()
            storageNavigatorMock.push(expectedFolder)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(
            storageNavigatorMock,
            getCurrentNavFolderFlowUseCaseMock,
            getValidationRulesUseCaseMock
        )
    }
}
