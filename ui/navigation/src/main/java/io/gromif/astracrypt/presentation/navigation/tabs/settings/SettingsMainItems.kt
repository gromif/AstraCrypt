package io.gromif.astracrypt.presentation.navigation.tabs.settings

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.astracrypt.resources.R

enum class SettingsMainItems(
    val imageVector: ImageVector,
    @StringRes val titleId: Int
) {
    Security(
        imageVector = Icons.Outlined.Security,
        titleId = R.string.settings_security
    ),
    Interface(
        imageVector = Icons.Outlined.Palette,
        titleId = R.string.settings_interface
    ),
    Donate(
        imageVector = Icons.Outlined.MonetizationOn,
        titleId = R.string.settings_donate
    ),
    About(
        imageVector = Icons.Outlined.Info,
        titleId = R.string.settings_about
    )
}
