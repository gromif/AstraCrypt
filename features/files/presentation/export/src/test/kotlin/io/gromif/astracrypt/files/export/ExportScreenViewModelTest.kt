package io.gromif.astracrypt.files.export

import android.net.Uri
import androidx.work.WorkManager
import io.gromif.astracrypt.files.domain.usecase.export.InternalExportUseCase
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.FilesUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ExportScreenViewModelTest {
    private lateinit var exportScreenViewModel: ExportScreenViewModel

    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val filesUtil: FilesUtil = mockk()
    private val workManager: WorkManager = mockk()
    private val internalExportUseCase: InternalExportUseCase = mockk()
    private val uriMapper: Mapper<String, Uri> = mockk()

    @Before
    fun setUp() {
        exportScreenViewModel = ExportScreenViewModel(
            defaultDispatcher = defaultDispatcher,
            secureContentContract = mockk(relaxed = true),
            filesUtil = filesUtil,
            workManager = workManager,
            internalExportUseCase = internalExportUseCase,
            uriMapper = uriMapper
        )
    }

    @Test
    fun `export calls privateExportUseCase with correct parameters and updates the state`() {
        val targetId: Long = 43
        val targetUiState = exportScreenViewModel.uiState.copy(isDone = true)

        coEvery { internalExportUseCase(targetId) } returns "sample"
        every { uriMapper(any()) } returns Uri.EMPTY
        exportScreenViewModel.export(targetId)

        coVerify(exactly = 1) { internalExportUseCase(targetId) }
        Assert.assertEquals(targetUiState, exportScreenViewModel.uiState)
    }
}
