package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
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
    private val itemWriter: ItemWriter = mockk(relaxed = true)

    @Before
    fun setUp() {
        renameUseCase = RenameUseCase(getAeadInfoUseCase, itemWriter)
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
            itemWriter.rename(
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
