package io.gromif.astracrypt.profile.presentation.settings.aead

import io.gromif.astracrypt.profile.domain.model.AeadMode
import io.gromif.astracrypt.profile.domain.usecase.GetAeadModeFlowUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetAeadModeUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AeadSettingsViewModelTest {
    private lateinit var viewModel: AeadSettingsViewModel

    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val setAeadModeUseCaseMock: SetAeadModeUseCase = mockk()
    private val getAeadModeFlowUseCaseMock: GetAeadModeFlowUseCase = mockk()
    private val targetAeadModeFlow = flow<AeadMode> { AeadMode.None }

    @Before
    fun setUp() {
        every { getAeadModeFlowUseCaseMock() } returns targetAeadModeFlow

        viewModel = AeadSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            setAeadModeUseCase = setAeadModeUseCaseMock,
            getAeadModeFlowUseCase = getAeadModeFlowUseCaseMock
        )
    }

    @Test
    fun `setSettingsAead calls setAeadModeUseCase with correct parameters`() {
        val targetAeadMode = AeadMode.None

        coJustRun { setAeadModeUseCaseMock(targetAeadMode) }

        viewModel.setSettingsAead(targetAeadMode)

        coVerify(exactly = 1) { setAeadModeUseCaseMock(targetAeadMode) }
    }
}