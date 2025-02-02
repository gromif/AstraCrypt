package io.gromif.astracrypt.files.files.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default

@Composable
internal fun renameDialog(
    name: String,
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
            maxLength = 256,
            singleLine = true
        ),
        onResult = onResult
    )
    return state
}