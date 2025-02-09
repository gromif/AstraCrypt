package io.gromif.astracrypt.presentation.navigation.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.BottomBarItems
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions

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
