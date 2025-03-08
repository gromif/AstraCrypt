package io.gromif.astracrypt.presentation.navigation.models

import androidx.compose.runtime.Composable

interface HostEvents {

    fun setUiState(uiState: UiState)

    @Composable
    fun ObserveFab(action: suspend (Any) -> Unit)

    @Composable
    fun ObserveToolbarActions(action: suspend (Any) -> Unit)

}