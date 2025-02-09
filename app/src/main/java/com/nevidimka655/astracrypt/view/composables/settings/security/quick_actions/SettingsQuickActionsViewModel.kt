package com.nevidimka655.astracrypt.view.composables.settings.security.quick_actions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.app.AppComponentService
import javax.inject.Inject

@HiltViewModel
class SettingsQuickActionsViewModel @Inject constructor(
    private val appComponentService: AppComponentService
): ViewModel() {
    /*var quickDataDeletion
        get() = appComponentService.quickDataDeletion
        set(value) { appComponentService.quickDataDeletion = value }*/

}