package com.nevidimka655.astracrypt.view.composables.shared.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default

@Composable
fun Dialogs.rename(
    state: MutableState<Boolean>,
    name: String,
    onResult: (String) -> Unit
) = DialogsCore.TextFields.default(
    state = state,
    title = stringResource(id = R.string.files_options_rename),
    params = DialogsCore.TextFields.Params(
        text = name,
        label = stringResource(id = R.string.name),
        maxLength = AppConfig.ITEM_NAME_MAX_SIZE,
        singleLine = true
    ),
    onResult = onResult
)