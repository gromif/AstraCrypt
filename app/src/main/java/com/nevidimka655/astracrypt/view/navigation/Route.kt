package com.nevidimka655.astracrypt.view.navigation

import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.R
import kotlinx.serialization.Serializable

object Route {
    object Tabs {
        @Serializable object Home
        @Serializable data class Files(val isStarred: Boolean = false)
        @Serializable object Settings
    }

    @Serializable
    data class Details(
        @StringRes val titleId: Int = R.string.files_options_details,
        val itemId: Long
    )

    @Serializable
    data class Export(val itemId: Long, val outUri: String? = null)

    @Serializable object EditProfile
    @Serializable object SettingsUi
    @Serializable object SettingsUiFiles
    @Serializable object SettingsSecurity
    @Serializable object SettingsSecurityAdmin
    @Serializable object SettingsSecurityQuickActions

    @Serializable
    object AboutGraph {
        @Serializable object About
        @Serializable object PrivacyPolicy
    }
}