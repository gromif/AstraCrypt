package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RenameUseCaseTest {
    private lateinit var renameUseCase: RenameUseCase
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        renameUseCase = RenameUseCase(getAeadInfoUseCase, repository)
    }

    @Test
    fun shouldTrimName_and_callRepositoryWithCorrectValues() {
        val id: Long = 1
        val rawName = "  New Folder  "
        val trimmedName = rawName.trim()
        val mockAeadInfo = mockk<AeadInfo>()

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        runBlocking { renameUseCase(id, rawName) }

        coVerify(exactly = 1) { getAeadInfoUseCase() }
        coVerify(exactly = 1) {
            repository.rename(
                aeadInfo = mockAeadInfo,
                id = id,
                name = trimmedName
            )
        }
    }

    @Test(expected = ValidationException.InvalidNameException::class)
    fun shouldThrowException_whenNameValidatorFails() {
        val id: Long = 1
        val name = "    "
        val mockAeadInfo = mockk<AeadInfo>()

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo

        runBlocking { renameUseCase(id, name) }
    }
}
