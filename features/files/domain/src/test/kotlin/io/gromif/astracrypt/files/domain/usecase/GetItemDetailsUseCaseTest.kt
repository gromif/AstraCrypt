package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetItemDetailsUseCaseTest {
    private lateinit var getItemDetailsUseCase: GetItemDetailsUseCase
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val itemReader: ItemReader = mockk(relaxed = true)

    @Before
    fun setUp() {
        getItemDetailsUseCase = GetItemDetailsUseCase(getAeadInfoUseCase, itemReader)
    }

    @Test
    fun shouldGetCurrentAeadInfo_and_returnItemDetails() {
        val id: Long = 5
        val mockAeadInfo = mockk<AeadInfo>()
        val expectedItemDetails = ItemDetails.Folder(
            name = "New folder",
            filesCount = 0,
            foldersCount = 0,
            creationTime = 0
        )

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo
        coEvery { itemReader.getItemDetails(mockAeadInfo, id) } returns expectedItemDetails

        runBlocking {
            val itemDetails = getItemDetailsUseCase(id)
            Assert.assertEquals(expectedItemDetails, itemDetails)
        }

        coVerify(exactly = 1) { getAeadInfoUseCase() }
        coVerify(exactly = 1) { itemReader.getItemDetails(mockAeadInfo, id) }
    }

    @Test(expected = ValidationException.InvalidNameException::class)
    fun whenNameIsInvalid_shouldThrowException() {
        val id: Long = 5
        val mockAeadInfo = mockk<AeadInfo>()
        val expectedItemDetails = ItemDetails.Folder(
            name = "",
            filesCount = 0,
            foldersCount = 0,
            creationTime = 0
        )

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo
        coEvery { itemReader.getItemDetails(mockAeadInfo, id) } returns expectedItemDetails

        runBlocking { getItemDetailsUseCase(id) }
    }

    @Test(expected = ValidationException.InvalidFileSizeException::class)
    fun whenFileSizeIsInvalid_shouldThrowException() {
        val id: Long = 5
        val mockAeadInfo = mockk<AeadInfo>()
        val expectedItemDetails = ItemDetails.File(
            name = "file",
            type = ItemType.Text,
            file = mockk(),
            preview = null,
            flags = null,
            size = -1,
            creationTime = 0L
        )

        coEvery { getAeadInfoUseCase() } returns mockAeadInfo
        coEvery { itemReader.getItemDetails(mockAeadInfo, id) } returns expectedItemDetails

        runBlocking { getItemDetailsUseCase(id) }
    }
}
