package com.nevidimka655.astracrypt.ui.tabs.settings

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.R

enum class SettingsMainItems(
    val imageVector: ImageVector,
    @StringRes val titleId: Int
) {

    EditProfile(
        imageVector = Icons.Outlined.Person,
        titleId = R.string.settings_editProfile
    ),
    Security(
        imageVector = Icons.Outlined.Security,
        titleId = R.string.settings_security
    ),
    Interface(
        imageVector = Icons.Outlined.Palette,
        titleId = R.string.settings_interface
    ),
    Purchases(
        imageVector = Icons.Outlined.Storefront,
        titleId = R.string.purchases
    ),
    About(
        imageVector = Icons.Outlined.Info,
        titleId = R.string.settings_about
    )

}