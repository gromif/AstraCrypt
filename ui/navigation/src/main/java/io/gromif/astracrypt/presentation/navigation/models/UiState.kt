package io.gromif.astracrypt.presentation.navigation.models

import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.astracrypt.presentation.navigation.BottomBarItems
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.ui.compose.core.wrappers.TextWrap

data class UiState(
    val toolbar: Toolbar = Toolbar(),
    val fab: Fab? = null,
    val bottomBarTab: BottomBarItems? = null,
    val searchBar: Boolean = false
) {

    data class Toolbar(
        val isContextual: Boolean = false,
        val title: TextWrap = TextWrap.Text(""),
        val actions: List<ToolbarActions.Action>? = null
    )

    data class Fab(
        val icon: ImageVector,
        val contentDescription: TextWrap = TextWrap.Text("")
    )
}
