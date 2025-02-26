package io.gromif.astracrypt.files.settings.aead.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.radio

@Composable
internal fun PreviewRadioPreference(
    options: List<String> = listOf(),
    selectedIndex: Int,
    onSelect: (Int) -> Unit = {}
) {
    var dialogThumbsState by DialogsCore.Selectable.radio(
        onSelected = onSelect,
        title = stringResource(id = R.string.thumbnail),
        items = options,
        selectedItemIndex = selectedIndex
    )

    Preference(
        titleText = stringResource(id = R.string.thumbnail),
        summaryText = options[selectedIndex]
    ) {
        dialogThumbsState = true
    }
}