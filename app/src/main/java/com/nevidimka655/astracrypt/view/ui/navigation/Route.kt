package com.nevidimka655.astracrypt.view.ui.navigation

import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.Actions
import com.nevidimka655.astracrypt.view.FabIcons
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.serialization.Serializable

object Route {

    object Tabs {

        @Serializable
        object Home {
            object Ui {
                val state = UiState(
                    toolbar = UiState.Toolbar(actions = Actions.Home),
                    bottomBarTab = BottomBarItems.Home
                )
            }
        }

        @Serializable
        data class Files(val isStarred: Boolean = false) {
            object Ui {
                val files = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.files)
                    ),
                    fab = UiState.Fab(icon = FabIcons.Add),
                    bottomBarTab = BottomBarItems.Files,
                    searchBar = true
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
        val itemId: Long,
        val outUri: String? = null
    ) {
        object Ui {
            val state = UiState(
                toolbar = UiState.Toolbar(
                    title = TextWrap.Resource(id = R.string.files_options_export)
                )
            )
        }
    }

    @Serializable
    object EditProfile {
        object Ui {
            val state = UiState(
                toolbar = UiState.Toolbar(
                    title = TextWrap.Resource(id = R.string.settings_editProfile)
                )
            )
        }
    }

    @Serializable
    object SettingsUi {
        object Ui {
            val state = UiState(
                toolbar = UiState.Toolbar(
                    title = TextWrap.Resource(id = R.string.settings_interface)
                )
            )
        }
    }

    @Serializable
    object SettingsUiFiles {
        object Ui {
            val state = UiState(
                toolbar = UiState.Toolbar(
                    title = TextWrap.Resource(id = R.string.files)
                )
            )
        }
    }

    @Serializable
    object SettingsSecurity {
        object Ui {
            val state = UiState(
                toolbar = UiState.Toolbar(
                    title = TextWrap.Resource(id = R.string.settings_security)
                )
            )
        }
    }

    @Serializable
    object AboutGraph {

        @Serializable
        object About {
            object Ui {
                val state = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.settings_about)
                    )
                )
            }
        }

        @Serializable
        object PrivacyPolicy {
            object Ui {
                val state = UiState(
                    toolbar = UiState.Toolbar(
                        title = TextWrap.Resource(id = R.string.privacyPolicy)
                    )
                )
            }
        }

    }

}