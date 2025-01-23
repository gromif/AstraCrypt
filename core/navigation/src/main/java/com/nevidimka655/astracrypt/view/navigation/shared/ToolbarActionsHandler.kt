package com.nevidimka655.astracrypt.view.navigation.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ToolbarActionsHandler(
    onToolbarActions: Flow<ToolbarActions.Action>,
    action: suspend (ToolbarActions.Action) -> Unit
) = LaunchedEffect(Unit) {
    onToolbarActions.collectLatest(action = action)
}