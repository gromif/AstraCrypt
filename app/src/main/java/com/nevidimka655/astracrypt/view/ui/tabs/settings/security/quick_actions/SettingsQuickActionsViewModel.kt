package com.nevidimka655.astracrypt.view.ui.tabs.settings.security.quick_actions

import androidx.lifecycle.ViewModel
import com.nevidimka655.astracrypt.app.utils.AppComponentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsQuickActionsViewModel @Inject constructor(
    private val appComponentManager: AppComponentManager
): ViewModel() {
    var quickDataDeletion
        get() = appComponentManager.quickDataDeletion
        set(value) { appComponentManager.quickDataDeletion = value }

}