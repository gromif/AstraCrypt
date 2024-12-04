package com.nevidimka655.astracrypt.ui.tabs.settings.ui

import androidx.compose.material.icons.Icons
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.ui.theme.reset
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.utils.datastore.AppearanceManager
import dagger.hilt.android.lifecycle.HiltViewModel
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
        Icons.reset()
    }

}