package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateFolderUseCaseTest {
    private lateinit var createFolderUseCase: CreateFolderUseCase
    private val getCurrentNavFolderFlowUseCaseMock: GetCurrentNavFolderFlowUseCase = mockk()
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        createFolderUseCase = CreateFolderUseCase(
            getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCaseMock,
            getAeadInfoUseCase = getAeadInfoUseCase,
            repository = repository
        )
    }

    @Test
    fun shouldTrimName_and_callRepositoryWithCorrectValues() = runTest {
        val targetFolderId = 66L
        val rawName = "  New Folder  "
        val targetNavFolderFlow = flowOf(StorageNavigator.Folder(targetFolderId, rawName))
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

        every { getCurrentNavFolderFlowUseCaseMock() } returns targetNavFolderFlow
        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        createFolderUseCase(rawName)

        coVerify(exactly = 1) { getCurrentNavFolderFlowUseCaseMock() }
        coVerify(exactly = 1) { getAeadInfoUseCase() }
        coVerify(exactly = 1) {
            repository.insert(aeadInfo = mockAeadInfo, importItemDto = targetImportItemDto)
        }
    }

    @Test(expected = ValidationException.InvalidNameException::class)
    fun shouldThrowException_whenNameValidatorFails() = runTest {
        val targetName = "    "
        val targetFolder = StorageNavigator.Folder(66L, targetName)
        val targetFlow = flowOf(targetFolder)
        val mockAeadInfo = mockk<AeadInfo>()

        every { getCurrentNavFolderFlowUseCaseMock() } returns targetFlow
        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        createFolderUseCase(targetName)
    }
}
