package com.nevidimka655.astracrypt.view.navigation.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

data class UiState(
    val toolbar: Toolbar = Toolbar(),
    val fab: Fab? = null,
    val bottomBarTab: BottomBarItems? = null,
    val searchBar: Boolean = false
) {

    data class Toolbar(
        val title: TextWrap = TextWrap.Resource(id = R.string.home),
        val actions: List<ToolbarActions.Action>? = null
    )

    data class Fab(
        val icon: ImageVector,
        val contentDescription: TextWrap = TextWrap.Text("")
    )

}
