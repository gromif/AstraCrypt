package io.gromif.astracrypt.files.settings.aead

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.SetAeadInfoUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AeadSettingsViewModelTest {
    private lateinit var aeadSettingsViewModel: AeadSettingsViewModel
    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val workManagerMock: WorkManager = mockk()
    private val setAeadInfoUseCaseMock: SetAeadInfoUseCase = mockk()
    private val getAeadInfoFlowUseCaseMock: GetAeadInfoFlowUseCase = mockk()

    private val currentAeadInfo: AeadInfo = AeadInfo()
    private val aeadInfoFlow = flow<AeadInfo> {  }

    @Before
    fun setUp() {
        every { getAeadInfoFlowUseCaseMock() } returns aeadInfoFlow

        aeadSettingsViewModel = AeadSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            workManager = workManagerMock,
            setAeadInfoUseCase = setAeadInfoUseCaseMock,
            getAeadInfoFlowUseCase = getAeadInfoFlowUseCaseMock,
        )
    }

    @Test
    fun `should start listening for AeadInfo changes`() {
        verify(exactly = 1) { getAeadInfoFlowUseCaseMock() }
    }

    @Test
    fun `should call setAeadInfoUseCase when updating the file mode`() {
        val targetFileMode = AeadMode.Template(id = 4, name = "")
        val targetAeadInfo = currentAeadInfo.copy(fileMode = targetFileMode)
        coJustRun { setAeadInfoUseCaseMock(targetAeadInfo) }

        aeadSettingsViewModel.setFileMode(targetFileMode)

        coVerify(exactly = 1) { setAeadInfoUseCaseMock(targetAeadInfo) }
    }

    @Test
    fun `should call setAeadInfoUseCase when updating the preview mode`() {
        val targetPreviewMode = AeadMode.Template(id = 4, name = "")
        val targetAeadInfo = currentAeadInfo.copy(previewMode = targetPreviewMode)
        coJustRun { setAeadInfoUseCaseMock(targetAeadInfo) }

        aeadSettingsViewModel.setPreviewMode(targetPreviewMode)

        coVerify(exactly = 1) { setAeadInfoUseCaseMock(targetAeadInfo) }
    }

    @Test
    fun `should enqueue Worker when updating the database mode`() {
        val targetDatabaseMode = AeadMode.Template(id = 4, name = "")
        justRun { workManagerMock.enqueue(any<OneTimeWorkRequest>()) }

        aeadSettingsViewModel.setDatabaseMode(targetDatabaseMode)

        verify(exactly = 1) { workManagerMock.enqueue(any<OneTimeWorkRequest>()) }
    }

}