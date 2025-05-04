package io.gromif.secure_content.presentation

import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.usecase.GetContentModeFlowUseCase
import io.gromif.secure_content.domain.usecase.SetContentModeUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SecureContentSettingsViewModelTest {
    private lateinit var vm: SecureContentSettingsViewModel

    private val defaultDispatcher = UnconfinedTestDispatcher()
    private val setContentModeUseCaseMock: SetContentModeUseCase = mockk()
    private val getContentModeFlowUseCaseMock: GetContentModeFlowUseCase = mockk()

    @Before
    fun setUp() {
        every { getContentModeFlowUseCaseMock() } returns emptyFlow()

        vm = SecureContentSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            setContentModeUseCase = setContentModeUseCaseMock,
            getContentModeFlowUseCase = getContentModeFlowUseCaseMock
        )
    }

    @Test
    fun `should properly get contentModeFlow`() {
        verify(exactly = 1) { getContentModeFlowUseCaseMock() }
    }

    @Test
    fun `setMode calls SetContentModeUseCase with correct parameters`() {
        val targetMode = SecureContentMode.DISABLED

        coJustRun { setContentModeUseCaseMock(targetMode) }
        vm.setMode(mode = targetMode)

        coVerify(exactly = 1) { setContentModeUseCaseMock(targetMode) }
    }

}