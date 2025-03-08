package io.gromif.astracrypt.files.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.usecase.GetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.SetListViewModeUseCase
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UiSettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val setListViewModeUseCase: SetListViewModeUseCase,
    getListViewModeUseCase: GetListViewModeUseCase
): ViewModel() {
    val viewModeState = getListViewModeUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), ViewMode.Grid
    )

    fun setViewMode(viewMode: ViewMode) = viewModelScope.launch(defaultDispatcher) {
        setListViewModeUseCase(viewMode)
    }

}
