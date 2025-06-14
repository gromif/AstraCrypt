package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class MoveUseCaseTest {
    private lateinit var moveUseCase: MoveUseCase
    private val getCurrentNavFolderFlowUseCaseMock: GetCurrentNavFolderFlowUseCase = mockk()
    private val itemWriter: ItemWriter = mockk(relaxed = true)

    @Before
    fun setUp() {
        moveUseCase = MoveUseCase(
            getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCaseMock,
            itemWriter = itemWriter
        )
    }

    @Test(expected = ValidationException.EmptyIdListException::class)
    fun shouldThrowException_whenIdListIsEmpty() = runTest {
        moveUseCase(emptyList())
    }

    @Test
    fun `should call repository with correct parameters`() = runTest {
        val targetFolderId = 5L
        val targetIds = listOf(targetFolderId)
        val targetNavFolderFlow = flowOf(StorageNavigator.Folder(targetFolderId, ""))

        every { getCurrentNavFolderFlowUseCaseMock() } returns targetNavFolderFlow

        moveUseCase(targetIds)

        coVerify { getCurrentNavFolderFlowUseCaseMock() }
        coVerify(exactly = 1) {
            itemWriter.move(targetIds, targetFolderId)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(getCurrentNavFolderFlowUseCaseMock, itemWriter)
    }
}
