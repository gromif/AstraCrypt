package io.gromif.astracrypt.presentation.navigation.settings.aead

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.notes.settings.AeadSettings
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.auth.presentation.settings.aead.AuthAeadSettingsScreen
import io.gromif.astracrypt.files.settings.aead.FilesAeadSettings
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.profile.presentation.settings.aead.ProfileAeadSettingsScreen

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