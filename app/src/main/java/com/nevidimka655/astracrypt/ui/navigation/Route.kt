package com.nevidimka655.astracrypt.ui.navigation

import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.R
import kotlinx.serialization.Serializable

object Route {

    object Tabs {

        @Serializable
        data class Home(
            @StringRes val titleId: Int = R.string.home
        )

        @Serializable
        data class Files(
            @StringRes val titleId: Int = R.string.files,
            @StringRes val titleIdAlt: Int = R.string.starred,
            val isStarred: Boolean = false
        )

        @Serializable
        data class Settings(
            @StringRes val titleId: Int = R.string.settings
        )

    }

}