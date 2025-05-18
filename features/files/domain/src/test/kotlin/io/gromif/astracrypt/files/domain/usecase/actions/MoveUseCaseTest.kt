package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class MoveUseCaseTest {
    private lateinit var moveUseCase: MoveUseCase
    private val getCurrentNavFolderUseCaseMock: GetCurrentNavFolderUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        moveUseCase = MoveUseCase(
            getCurrentNavFolderUseCase = getCurrentNavFolderUseCaseMock,
            repository = repository
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

        every { getCurrentNavFolderUseCaseMock().id } returns targetFolderId

        moveUseCase(targetIds)

        verify { getCurrentNavFolderUseCaseMock().id }
        coVerify(exactly = 1) {
            repository.move(targetIds, targetFolderId)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(getCurrentNavFolderUseCaseMock, repository)
    }
}
