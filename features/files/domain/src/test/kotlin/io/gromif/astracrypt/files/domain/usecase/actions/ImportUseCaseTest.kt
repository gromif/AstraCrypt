package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.service.ClockService
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ImportUseCaseTest {
    private lateinit var importUseCase: ImportUseCase
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val clockService: ClockService = mockk(relaxed = true)
    private val repository: Repository = mockk(relaxed = true)
    private val fileUtilFactory: FileUtil.Factory = mockk()
    private val previewUtil: PreviewUtil = mockk()
    private val flagsUtil: FlagsUtil = mockk()

    private val mockkAeadInfo = mockk<AeadInfo>()

    @Before
    fun setUp() {
        importUseCase = ImportUseCase(
            getAeadInfoUseCase, clockService, repository, fileUtilFactory, previewUtil, flagsUtil
        )
    }

    @Test
    fun shouldGetCurrentAeadInfo_and_importFile() = runTest {
        val fileUtil: FileUtil = mockk()
        val pathList = listOf("file1.txt", "file2.txt")
        val parentId = 42L
        val saveSource = false
        val fileName = "TestFile"
        val filePath = "/storage/emulated/0/TestFile"
        val previewPath = "/storage/emulated/0/preview_TestFile"
        val fileType = mockk<ItemType>()
        val flags: String? = null
        val creationTime = 123456789L
        val fileSize = 1024L

        val targetImportItemDto = ImportItemDto(
            parent = parentId,
            name = fileName,
            itemState = ItemState.Default,
            itemType = fileType,
            file = filePath,
            preview = previewPath,
            flags = flags,
            creationTime = creationTime,
            size = fileSize
        )

        coEvery { getAeadInfoUseCase() } returns mockkAeadInfo
        coEvery { fileUtilFactory.create() } returns fileUtil
        coEvery { fileUtil.open(any()) } returns true
        every { fileUtil.getName() } returns fileName
        every { fileUtil.parseType() } returns fileType
        every { fileUtil.creationTime() } returns creationTime
        every { fileUtil.length() } returns fileSize
        coEvery { fileUtil.write() } returns filePath
        coEvery { previewUtil.getPreviewPath(any(), any()) } returns previewPath
        coEvery { flagsUtil.getFlags(any(), any()) } returns null
        justRun { fileUtil.delete() }

        importUseCase(pathList, parentId, saveSource)

        verify(exactly = pathList.size) { fileUtil.open(any()) }
        verify(exactly = pathList.size) { fileUtil.getName() }
        verify(exactly = pathList.size) { fileUtil.parseType() }
        verify(exactly = pathList.size) { fileUtil.length() }
        coVerify(exactly = pathList.size) { fileUtil.write() }
        coVerify(exactly = pathList.size) { previewUtil.getPreviewPath(fileType, any()) }
        coVerify(exactly = pathList.size) { flagsUtil.getFlags(fileType, any()) }

        coVerify(exactly = pathList.size) { repository.insert(mockkAeadInfo, targetImportItemDto) }
        verify(exactly = pathList.size) { fileUtil.delete() }
    }

    /*@Test(expected = ValidationException.InvalidNameException::class)
    fun whenNameIsNull_shouldThrowException() = runTest {
        val fileUtil: FileUtil = mockk()
        val pathList = listOf("file1.txt", "file2.txt")
        val parentId = 42L
        val saveSource = false

        coEvery { getAeadInfoUseCase() } returns mockkAeadInfo
        coEvery { fileUtilFactory.create() } returns fileUtil
        coEvery { fileUtil.open(any()) } returns true
        every { fileUtil.getName() } returns null

        importUseCase(pathList, parentId, saveSource)
    }

    @Test(expected = ValidationException.InvalidFileSizeException::class)
    fun whenFileSizeIsLessThanZero_shouldThrowException() = runTest {
        val fileUtil: FileUtil = mockk()
        val pathList = listOf("file1.txt", "file2.txt")
        val parentId = 42L
        val saveSource = false
        val fileName = "TestFile"
        val fileType = mockk<ItemType>()
        val creationTime = 123456789L
        val fileSize: Long = -1

        coEvery { getAeadInfoUseCase() } returns mockkAeadInfo
        coEvery { fileUtilFactory.create() } returns fileUtil
        coEvery { fileUtil.open(any()) } returns true
        every { fileUtil.getName() } returns fileName
        every { fileUtil.parseType() } returns fileType
        every { fileUtil.creationTime() } returns creationTime
        every { fileUtil.length() } returns fileSize

        importUseCase(pathList, parentId, saveSource)
    }

    @Test(expected = ValidationException.InvalidPathException::class)
    fun whenFilePathIsNull_shouldThrowException() = runTest {
        val fileUtil: FileUtil = mockk()
        val pathList = listOf("file1.txt", "file2.txt")
        val parentId = 42L
        val saveSource = false
        val fileName = "TestFile"
        val fileType = mockk<ItemType>()
        val creationTime = 123456789L
        val fileSize: Long = 1024
        val filePath: String? = null

        coEvery { getAeadInfoUseCase() } returns mockkAeadInfo
        coEvery { fileUtilFactory.create() } returns fileUtil
        coEvery { fileUtil.open(any()) } returns true
        every { fileUtil.getName() } returns fileName
        every { fileUtil.parseType() } returns fileType
        every { fileUtil.creationTime() } returns creationTime
        every { fileUtil.length() } returns fileSize
        coEvery { fileUtil.write() } returns filePath

        importUseCase(pathList, parentId, saveSource)
    }*/
}
