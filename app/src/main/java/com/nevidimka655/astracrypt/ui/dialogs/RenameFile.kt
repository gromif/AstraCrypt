package com.nevidimka655.astracrypt.ui.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.tabs.Tabs
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.extensions.removeLines
import com.nevidimka655.ui.compose_core.dialogs.Dialogs
import com.nevidimka655.ui.compose_core.dialogs.default

@Composable
fun Tabs.Files.Dialogs.rename(
    state: MutableState<Boolean>,
    name: String,
    onResult: (String) -> Unit
) = Dialogs.TextFields.default(
    state = state,
    title = stringResource(id = R.string.files_options_rename),
    params = Dialogs.TextFields.Params(
        text = name.removeLines(),
        label = stringResource(id = R.string.name),
        maxLength = AppConfig.ITEM_NAME_MAX_SIZE,
        singleLine = true
    ),
    onResult = onResult
)