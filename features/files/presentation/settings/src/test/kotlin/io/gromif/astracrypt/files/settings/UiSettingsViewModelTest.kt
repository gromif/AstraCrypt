package io.gromif.astracrypt.files.settings

import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.usecase.preferences.GetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.preferences.SetListViewModeUseCase
import io.mockk.coJustRun
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
class UiSettingsViewModelTest {
    private lateinit var uiSettingsViewModel: UiSettingsViewModel
    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val setListViewModeUseCaseMock: SetListViewModeUseCase = mockk()
    private val getListViewModeUseCaseMock: GetListViewModeUseCase = mockk()
    private val viewModeFlow = flow<ViewMode> { ViewMode.Grid }

    @Before
    fun setUp() {
        every { getListViewModeUseCaseMock() } returns viewModeFlow

        uiSettingsViewModel = UiSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            setListViewModeUseCase = setListViewModeUseCaseMock,
            getListViewModeUseCase = getListViewModeUseCaseMock
        )
    }

    @Test
    fun `should start listening for viewMode changes`() {
        verify { getListViewModeUseCaseMock() }
    }

    @Test
    fun `should call setListViewModeUseCase when updating the view mode settings`() {
        val targetViewMode: ViewMode = mockk()

        coJustRun { setListViewModeUseCaseMock(targetViewMode) }

        uiSettingsViewModel.setViewMode(targetViewMode)

        coVerify { setListViewModeUseCaseMock(targetViewMode) }
    }
}