package io.gromif.astracrypt.auth.presentation.settings.aead

import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.auth.domain.usecase.GetAeadModeFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetAeadModeUseCase
import io.mockk.coVerify
import io.mockk.every
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
    private val setAeadModeUseCaseMock: SetAeadModeUseCase = mockk()
    private val getAeadModeFlowUseCaseMock: GetAeadModeFlowUseCase = mockk()
    private val targetAeadModeFlow = flow<AeadMode> { AeadMode.None }

    @Before
    fun setUp() {
        every { getAeadModeFlowUseCaseMock() } returns targetAeadModeFlow

        aeadSettingsViewModel = AeadSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            setAeadModeUseCase = setAeadModeUseCaseMock,
            getAeadModeFlowUseCase = getAeadModeFlowUseCaseMock
        )
    }

    @Test
    fun `should start listening for auth changes`() {
        verify(exactly = 1) { getAeadModeFlowUseCaseMock() }
    }

    @Test
    fun `should call setAeadModeUseCase when updating AEAD settings`() {
        val targetAeadMode = AeadMode.None

        aeadSettingsViewModel.setSettingsAead(targetAeadMode)

        coVerify(exactly = 1) { setAeadModeUseCaseMock(targetAeadMode) }
    }
}