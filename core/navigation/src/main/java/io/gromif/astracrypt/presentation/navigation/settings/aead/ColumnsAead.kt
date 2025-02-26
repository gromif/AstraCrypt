package io.gromif.astracrypt.presentation.navigation.settings.aead

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.settings.aead.columns.FilesColumnsAeadSettingsScreen
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.update_database
import io.gromif.astracrypt.presentation.navigation.shared.ToolbarActionsObserver
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_columns),
        actions = listOf(ToolbarActions.update_database)
    )
)

fun NavGraphBuilder.settingsSecurityColumnsAead(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
) = composable<Route.SettingsSecurityColumnsAead> {
    UiStateHandler { onUiStateChange(ScreenUiState) }

    val setColumnsAeadEventChannel = remember { Channel<Unit>() }

    ToolbarActionsObserver(onToolbarActions) {
        setColumnsAeadEventChannel.send(Unit)
    }

    FilesColumnsAeadSettingsScreen(
        setColumnsAeadEventFlow = setColumnsAeadEventChannel.receiveAsFlow()
    )
}