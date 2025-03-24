package io.gromif.lab_zip.presentation

import android.net.Uri
import androidx.work.WorkManager
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.WorkerSerializer
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.lab_zip.domain.FileInfo
import io.gromif.lab_zip.domain.usecase.GetFileInfosUseCase
import io.gromif.lab_zip.domain.usecase.GetSourceFileInfoUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CombineZipViewModelTest {
    private val defaultDispatcher = UnconfinedTestDispatcher()
    private val getSourceFileInfoUseCase: GetSourceFileInfoUseCase = mockk()
    private val getFileInfosUseCase: GetFileInfosUseCase = mockk()
    private val workManager: WorkManager = mockk(relaxed = true)
    private val workerSerializer: WorkerSerializer = mockk()
    private val base64Encoder: Base64Encoder = mockk()
    private val uriToStringMapper: Mapper<Uri, String> = mockk()

    private lateinit var viewModel: CombineZipViewModel

    @Before
    fun setup() {
        viewModel = CombineZipViewModel(
            defaultDispatcher = defaultDispatcher,
            getSourceFileInfoUseCase = getSourceFileInfoUseCase,
            getFileInfosUseCase = getFileInfosUseCase,
            workManager = workManager,
            workerSerializer = workerSerializer,
            base64Encoder = base64Encoder,
            uriToStringMapper = uriToStringMapper
        )
    }

    @Test
    fun `setSource updates sourceState`() = runTest {
        val uri: Uri = mockk()
        val uriString = "mocked_uri_string"
        val fileInfo: FileInfo = mockk()

        every { uriToStringMapper.invoke(uri) } returns uriString
        coEvery { getSourceFileInfoUseCase.invoke(uriString) } returns fileInfo

        viewModel.setSource(uri)

        Assert.assertEquals(fileInfo, viewModel.sourceState)
    }

    @Test
    fun `addFiles updates filesListState`() = runTest {
        val uris: List<Uri> = listOf(mockk(), mockk())
        val uriStrings: List<String> = listOf("uri_1", "uri_2")
        val fileInfos: List<FileInfo> = listOf(mockk(), mockk())

        uris.forEachIndexed { index, uri ->
            every { uriToStringMapper.invoke(uri) } returns uriStrings[index]
        }
        coEvery { getFileInfosUseCase.invoke(uriStrings) } returns fileInfos

        viewModel.addFiles(uris)

        Assert.assertEquals(fileInfos, viewModel.filesListState)
    }

    @Test
    fun `resetSource sets sourceState to null`() {
        viewModel.resetSource()
        assertNull(viewModel.sourceState)
    }

    @Test
    fun `resetFiles clears filesListState`() {
        viewModel.filesListState.add(mockk())
        viewModel.resetFiles()
        Assert.assertTrue(viewModel.filesListState.isEmpty())
    }
}