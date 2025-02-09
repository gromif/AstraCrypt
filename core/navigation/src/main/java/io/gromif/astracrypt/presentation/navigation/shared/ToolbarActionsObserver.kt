package io.gromif.astracrypt.presentation.navigation.shared

import androidx.compose.runtime.Composable
import com.nevidimka655.ui.compose_core.ext.FlowObserver
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ToolbarActionsObserver(
    onToolbarActions: Flow<ToolbarActions.Action>,
    action: suspend (ToolbarActions.Action) -> Unit
) = FlowObserver(onToolbarActions, action)