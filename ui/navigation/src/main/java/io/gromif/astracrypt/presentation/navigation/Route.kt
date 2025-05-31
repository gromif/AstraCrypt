package io.gromif.astracrypt.presentation.navigation

import kotlinx.serialization.Serializable

object Route {
    object AuthGraph {
        @Serializable object Password
    }

    object Tabs {
        @Serializable object Home

        @Serializable data class Files(
            val startParentId: Long? = null,
            val startParentName: String = ""
        )

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
    data class Details(val id: Long)

    @Serializable data class Export(
        val isExternalExport: Boolean,
        val itemId: Long,
        val outUri: String? = null
    )

    @Serializable object EditProfile

    @Serializable object SettingsUi

    @Serializable object SettingsUiFiles

    @Serializable object SettingsSecurity

    @Serializable object SettingsSecurityAead

    @Serializable object SettingsSecurityColumnsAead

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
