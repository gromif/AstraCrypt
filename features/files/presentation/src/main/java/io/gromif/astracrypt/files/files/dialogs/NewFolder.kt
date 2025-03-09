package io.gromif.astracrypt.files.files.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Compose
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.default

@Composable
internal fun newFolderDialog(
    state: MutableState<Boolean> = Compose.state(),
    maxLength: Int,
    onCreate: (String) -> Unit
) = DialogsCore.TextFields.default(
    state = state,
    title = stringResource(id = R.string.newFolder),
    params = DialogsCore.TextFields.Params(
        text = stringResource(id = R.string.untitledFolder),
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = maxLength,
    ),
    onResult = onCreate
)