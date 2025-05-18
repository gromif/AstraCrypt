package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateFolderUseCaseTest {
    private lateinit var createFolderUseCase: CreateFolderUseCase
    private val getCurrentNavFolderUseCaseMock: GetCurrentNavFolderUseCase = mockk()
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        createFolderUseCase = CreateFolderUseCase(
            getCurrentNavFolderUseCase = getCurrentNavFolderUseCaseMock,
            getAeadInfoUseCase = getAeadInfoUseCase,
            repository = repository
        )
    }

    @Test
    fun shouldTrimName_and_callRepositoryWithCorrectValues() = runTest {
        val targetFolderId = 66L
        val rawName = "  New Folder  "
        val trimmedName = rawName.trim()
        val mockAeadInfo = mockk<AeadInfo>()

        val targetImportItemDto = ImportItemDto(
            parent = targetFolderId,
            name = trimmedName,
            itemState = ItemState.Default,
            itemType = ItemType.Folder,
            file = null,
            preview = null,
            flags = null,
            creationTime = 0,
            size = 0
        )

        every { getCurrentNavFolderUseCaseMock().id } returns targetFolderId
        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        createFolderUseCase(rawName)

        verify(exactly = 1) { getCurrentNavFolderUseCaseMock().id }
        coVerify(exactly = 1) { getAeadInfoUseCase() }
        coVerify(exactly = 1) {
            repository.insert(aeadInfo = mockAeadInfo, importItemDto = targetImportItemDto)
        }
    }

    @Test(expected = ValidationException.InvalidNameException::class)
    fun shouldThrowException_whenNameValidatorFails() = runTest {
        val targetFolderId = 66L
        val name = "    "
        val mockAeadInfo = mockk<AeadInfo>()

        every { getCurrentNavFolderUseCaseMock().id } returns targetFolderId
        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        createFolderUseCase(name)
    }
}
