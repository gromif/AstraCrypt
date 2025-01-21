package com.nevidimka655.astracrypt.view.navigation

import kotlinx.serialization.Serializable
import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.resources.R

object Route {
    object AuthGraph {
        @Serializable object Password
    }

    object Tabs {
        @Serializable object Home
        @Serializable object Files
        @Serializable object Starred
        @Serializable object Settings
    }

    @Serializable
    object LabGraph {
        @Serializable object List

        @Serializable
        object TinkGraph {
            @Serializable object Key
            @Serializable data class Text(val rawKeyset: String)
            @Serializable data class Files(val rawKeyset: String)
        }

        @Serializable object CombinedZip
    }

    @Serializable
    object NotesGraph {
        @Serializable object List

        @Serializable data class Overview(val noteId: Long = -1)
    }

    @Serializable
    data class Details(
        @StringRes val titleId: Int = R.string.files_options_details,
        val itemId: Long
    )

    @Serializable data class Export(val itemId: Long, val outUri: String? = null)

    @Serializable object EditProfile
    @Serializable object SettingsUi
    @Serializable object SettingsUiFiles
    @Serializable object SettingsSecurity
    @Serializable object SettingsSecurityAead
    @Serializable object SettingsSecurityAdmin
    @Serializable object SettingsSecurityAuth
    @Serializable object SettingsSecurityQuickActions

    @Serializable
    object AboutGraph {
        @Serializable object About
        @Serializable object PrivacyPolicy
    }

    @Serializable data class Help(val helpList: String)
}