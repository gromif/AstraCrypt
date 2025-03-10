package io.gromif.astracrypt.presentation.navigation.models

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow

@Stable
data class HostStateHolder(
    val dynamicThemeState: Boolean,
    val snackbarHostState: SnackbarHostState,
    val searchQueryState: StateFlow<String>,
)