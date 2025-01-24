package com.nevidimka655.astracrypt.view.navigation.shared

import androidx.compose.runtime.Composable
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.ui.compose_core.ext.FlowObserver
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ToolbarActionsHandler(
    onToolbarActions: Flow<ToolbarActions.Action>,
    action: suspend (ToolbarActions.Action) -> Unit
) = FlowObserver(onToolbarActions, action)