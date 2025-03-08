package io.gromif.astracrypt.presentation.navigation.models

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.flow.StateFlow

data class HostStateHolder(
    val dynamicThemeState: Boolean,
    val snackbarHostState: SnackbarHostState,
    val searchQueryState: StateFlow<String>,
)