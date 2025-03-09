package io.gromif.astracrypt.presentation.navigation.settings.aead

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.auth.presentation.settings.aead.AuthAeadSettingsScreen
import io.gromif.astracrypt.files.settings.aead.FilesAeadSettings
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.profile.presentation.settings.aead.ProfileAeadSettingsScreen
import io.gromif.astracrypt.resources.R
import io.gromif.notes.presentation.settings.AeadSettings
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.wrappers.TextWrap

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_encryption)
    )
)

internal fun NavGraphBuilder.settingsSecurityAead() = composable<Route.SettingsSecurityAead> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    PreferencesScreen {
        FilesAeadSettings(
            toDatabaseColumnsAeadSettings = {
                navController.navigate(Route.SettingsSecurityColumnsAead)
            }
        )
        AeadSettings()
        AuthAeadSettingsScreen()
        ProfileAeadSettingsScreen()
    }
}