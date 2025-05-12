package io.gromif.ui.compose.core.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

@Composable
fun RadioPreference(
    title: String,
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
) {
    var dialogState by DialogsCore.Selectable.radio(
        onSelected = onSelected,
        title = title,
        items = items,
        selectedItemIndex = selectedIndex
    )
    Preference(
        titleText = title,
        summaryText = items[selectedIndex]
    ) { dialogState = true }
}