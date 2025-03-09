package io.gromif.astracrypt.files.files.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Compose
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.default

@Composable
internal fun renameDialog(
    name: String,
    maxLength: Int,
    onResult: (String) -> Unit
): MutableState<Boolean> {
    val state = Compose.state()
    if (!state.value) return state
    DialogsCore.TextFields.default(
        state = state,
        title = stringResource(id = R.string.files_options_rename),
        params = DialogsCore.TextFields.Params(
            text = name,
            label = stringResource(id = R.string.name),
            maxLength = maxLength,
            singleLine = true
        ),
        onResult = onResult
    )
    return state
}