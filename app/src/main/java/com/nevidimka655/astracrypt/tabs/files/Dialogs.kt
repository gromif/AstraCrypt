package com.nevidimka655.astracrypt.tabs.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.tabs.Tabs
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.ui.compose_core.dialogs.Dialogs
import com.nevidimka655.ui.compose_core.dialogs.default

@Composable
fun Tabs.Files.Dialogs.newFolder(
    state: MutableState<Boolean>,
    onCreate: (String) -> Unit
) = Dialogs.TextFields.default(
    state = state,
    title = stringResource(id = R.string.newFolder),
    params = Dialogs.TextFields.Params(
        text = stringResource(id = R.string.untitledFolder),
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = AppConfig.ITEM_NAME_MAX_SIZE,
    ),
    onResult = onCreate
)