package io.gromif.astracrypt.files.settings.aead.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

@Composable
internal fun FileRadioPreference(
    options: List<String> = listOf(),
    selectedIndex: Int = 0,
    onSelect: (Int) -> Unit = {}
) {
    var dialogFilesState by DialogsCore.Selectable.radio(
        onSelected = onSelect,
        title = stringResource(id = R.string.files),
        items = options,
        selectedItemIndex = selectedIndex
    )

    Preference(
        titleText = stringResource(id = R.string.files),
        summaryText = options[selectedIndex]
    ) {
        dialogFilesState = true
    }
}