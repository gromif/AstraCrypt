package io.gromif.astracrypt.files.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default

@Composable
internal fun newFolderDialog(
    state: MutableState<Boolean> = Compose.state(),
    onCreate: (String) -> Unit
) = DialogsCore.TextFields.default(
    state = state,
    title = stringResource(id = R.string.newFolder),
    params = DialogsCore.TextFields.Params(
        text = stringResource(id = R.string.untitledFolder),
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = 256,
    ),
    onResult = onCreate
)