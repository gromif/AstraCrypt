package com.nevidimka655.astracrypt.view

import com.nevidimka655.astracrypt.data.model.ToolbarAction
import com.nevidimka655.astracrypt.view.ui.tabs.home.homeActions

enum class Actions(val list: List<ToolbarAction>) {
    Home(Screen.homeActions());

    object Screen
}