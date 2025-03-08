package io.gromif.astracrypt.quick_actions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.app.AppComponentService
import javax.inject.Inject

@HiltViewModel
internal class SettingsQuickActionsViewModel @Inject constructor(
    private val appComponentService: AppComponentService,
) : ViewModel() {
    var wipeTile by mutableStateOf(false)
        private set

    fun setWipeTileState(state: Boolean) {
        wipeTile = state
        appComponentService.wipeTile = state
    }

    init {
        wipeTile = appComponentService.wipeTile
    }

}