package com.nevidimka655.astracrypt.ui.navigation

import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.FabIcons
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.serialization.Serializable

object Route {

    object Tabs {

        @Serializable
        object Home {
            object Ui {
                val state = UiState(bottomBarTab = BottomBarItems.Home)
            }
        }

        @Serializable
        data class Files(
            val isStarred: Boolean = false
        ) {
            object Ui {
                val files = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.files)
                    ),
                    fab = UiState.Fab(icon = FabIcons.Add),
                    bottomBarTab = BottomBarItems.Files
                )
                val starred = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.starred)
                    ),
                    bottomBarTab = BottomBarItems.Starred
                )
            }
        }

        @Serializable
        object Settings {
            object Ui {
                val state = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.settings)
                    ),
                    bottomBarTab = BottomBarItems.Settings
                )
            }
        }

        @Serializable
        data class Details(
            @StringRes val titleId: Int = R.string.files_options_details,
            val itemId: Long
        ) {
            object Ui {
                val state = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.files_options_details)
                    )
                )
            }
        }

        @Serializable
        data class Export(
            @StringRes val titleId: Int = R.string.files_options_export,
            val itemId: Long,
            val outUri: String? = null
        )

    }

}