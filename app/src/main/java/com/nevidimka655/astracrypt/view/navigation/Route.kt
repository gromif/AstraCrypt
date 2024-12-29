@file:Suppress("ClassName")

package com.nevidimka655.astracrypt.view.navigation

import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.resources.R
import kotlinx.serialization.Serializable

object Route {
    object Tabs {
        @Serializable object Home
        @Serializable data class Files(val isStarred: Boolean = false)
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

        @Serializable
        data class Overview(val noteId: Long = -1)
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
    @Serializable object SettingsSecurityAuth
    @Serializable object SettingsSecurityQuickActions

    @Serializable
    object AboutGraph {
        @Serializable object About
        @Serializable object PrivacyPolicy
    }
}