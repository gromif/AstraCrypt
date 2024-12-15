package com.nevidimka655.astracrypt.view.models

import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.data.model.ToolbarAction
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

data class UiState(
    val toolbar: Toolbar = Toolbar(),
    val fab: Fab = Fab(false, FabIcons.Add),
    val bottomBarTab: BottomBarItems? = null,
    val searchBar: Boolean = false
) {

    data class Toolbar(
        val title: TextWrap = TextWrap.Resource(id = R.string.home),
        val actions: List<ToolbarAction>? = null
    )

    data class Fab(
        val visible: Boolean = true,
        val icon: FabIcons,
        val contentDescription: TextWrap = TextWrap.Text("")
    )

}
