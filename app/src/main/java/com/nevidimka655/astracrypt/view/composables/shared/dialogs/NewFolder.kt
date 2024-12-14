package com.nevidimka655.astracrypt.view.composables.shared.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default

@Composable
fun Dialogs.newFolder(
    state: MutableState<Boolean>,
    onCreate: (String) -> Unit
) = DialogsCore.TextFields.default(
    state = state,
    title = stringResource(id = R.string.newFolder),
    params = DialogsCore.TextFields.Params(
        text = stringResource(id = R.string.untitledFolder),
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = AppConfig.ITEM_NAME_MAX_SIZE,
    ),
    onResult = onCreate
)