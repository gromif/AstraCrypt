package io.gromif.astracrypt.quick_actions

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SettingsQuickActionsScreen() {
    val vm: SettingsQuickActionsViewModel = hiltViewModel()

    QuickActionsScreen(
        quickDataDeletion = vm.wipeTile,
        onSetQuickDataDeletion = vm::setWipeTileState
    )
}