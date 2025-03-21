package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateFolderUseCaseTest {
    private lateinit var createFolderUseCase: CreateFolderUseCase
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        createFolderUseCase = CreateFolderUseCase(getAeadInfoUseCase, repository)
    }

    @Test
    fun shouldTrimName_and_callRepositoryWithCorrectValues() {
        val rawName = "  New Folder  "
        val trimmedName = rawName.trim()
        val parentId: Long? = 42
        val mockAeadInfo = mockk<AeadInfo>()

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        runBlocking { createFolderUseCase(rawName, parentId) }

        coVerify(exactly = 1) { getAeadInfoUseCase() }
        coVerify(exactly = 1) {
            repository.insert(
                aeadInfo = mockAeadInfo,
                name = trimmedName,
                parent = 42,
                itemType = ItemType.Folder
            )
        }
    }

    @Test
    fun shouldDefaultParentIdToZero_whenNullIsProvided() {
        val name = "Test Folder"
        val mockAeadInfo = mockk<AeadInfo>()

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        runBlocking { createFolderUseCase(name, null) }

        coVerify(exactly = 1) {
            repository.insert(
                aeadInfo = mockAeadInfo,
                name = name,
                parent = 0,
                itemType = ItemType.Folder
            )
        }
    }

    @Test(expected = ValidationException.InvalidNameException::class)
    fun shouldThrowException_whenNameValidatorFails() {
        val name = "    "
        val parentId: Long? = 42
        val mockAeadInfo = mockk<AeadInfo>()

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        runBlocking { createFolderUseCase(name, parentId) }
    }

}