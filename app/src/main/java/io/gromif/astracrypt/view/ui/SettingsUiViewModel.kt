package io.gromif.astracrypt.view.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.data.datastore.AppearanceManager
import io.gromif.astracrypt.utils.Api
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsUiViewModel @Inject constructor(
    private val appearanceManager: AppearanceManager
) : ViewModel() {
    val isDynamicColorsSupported = Api.atLeast12()
    val dynamicThemeFlow get() = appearanceManager.dynamicThemeFlow

    fun setDynamicColorsState(enabled: Boolean) = viewModelScope.launch {
        appearanceManager.setDynamicTheme(enabled = enabled)
    }

}