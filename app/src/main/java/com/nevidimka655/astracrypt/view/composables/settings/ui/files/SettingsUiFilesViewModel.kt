package com.nevidimka655.astracrypt.view.composables.settings.ui.files

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.view.models.ViewMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsUiFilesViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val appearanceManager: AppearanceManager
): ViewModel() {
    val filesViewModeFlow: Flow<ViewMode> = appearanceManager.filesViewModeFlow

    fun setFilesViewMode(viewMode: ViewMode) = viewModelScope.launch(defaultDispatcher) {
        appearanceManager.setFilesViewMode(viewMode)
    }

}