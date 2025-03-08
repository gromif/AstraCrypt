package io.gromif.astracrypt.presentation.navigation

import androidx.navigation.NavGraphBuilder
import io.gromif.astracrypt.presentation.navigation.auth.settingsSecurityAuth
import io.gromif.astracrypt.presentation.navigation.help.help
import io.gromif.astracrypt.presentation.navigation.lab.labGraph
import io.gromif.astracrypt.presentation.navigation.models.NavParams
import io.gromif.astracrypt.presentation.navigation.notes.notesGraph
import io.gromif.astracrypt.presentation.navigation.settings.about.aboutGraph
import io.gromif.astracrypt.presentation.navigation.settings.aead.settingsSecurityAead
import io.gromif.astracrypt.presentation.navigation.settings.aead.settingsSecurityColumnsAead
import io.gromif.astracrypt.presentation.navigation.settings.profileSettings
import io.gromif.astracrypt.presentation.navigation.settings.quickActionsSettings
import io.gromif.astracrypt.presentation.navigation.settings.security.settingsSecurity
import io.gromif.astracrypt.presentation.navigation.settings.settingsSecurityAdmin
import io.gromif.astracrypt.presentation.navigation.settings.ui.filesUiSettings
import io.gromif.astracrypt.presentation.navigation.settings.ui.settingsUi
import io.gromif.astracrypt.presentation.navigation.tabs.files.details
import io.gromif.astracrypt.presentation.navigation.tabs.files.export
import io.gromif.astracrypt.presentation.navigation.tabs.tabsGraph

internal fun root(
    navParams: NavParams,
    onDynamicColorsStateChange: (Boolean) -> Unit,
): NavGraphBuilder.() -> Unit = {
    tabsGraph()
    notesGraph()
    details()
    export()

    labGraph()

    // settings
    profileSettings()
    settingsUi(
        isDynamicColorsSupported = navParams.isDynamicColorsSupported,
        onDynamicColorsStateChange = onDynamicColorsStateChange,
    )
    filesUiSettings()
    settingsSecurity(isActionsSupported = navParams.isActionsSupported)
    settingsSecurityAdmin()
    settingsSecurityAuth()
    settingsSecurityAead()
    settingsSecurityColumnsAead()
    quickActionsSettings()
    aboutGraph(applicationVersion = navParams.applicationVersion)

    help()
}