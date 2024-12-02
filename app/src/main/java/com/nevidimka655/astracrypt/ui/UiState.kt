package com.nevidimka655.astracrypt.ui

import androidx.compose.runtime.saveable.Saver
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.navigation.BottomBarItems
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

data class UiState(
    val toolbar: Toolbar = Toolbar(),
    val fab: Fab = Fab(false, FabIcons.Add),
    val bottomBarTab: BottomBarItems? = null
) {

    companion object {
        val saver = Saver<UiState, Any>(
            save = {
                listOf(
                    it.bottomBarTab
                )
            },
            restore = {
                val params = it as List<*>
                UiState(
                    bottomBarTab = params[0] as BottomBarItems
                )
            }
        )
    }

    data class Toolbar(
        val title: TextWrap = TextWrap.Resource(id = R.string.home)
    )

    data class Fab(
        val visible: Boolean = true,
        val icon: FabIcons,
        val contentDescription: TextWrap = TextWrap.Text("")
    )

}
