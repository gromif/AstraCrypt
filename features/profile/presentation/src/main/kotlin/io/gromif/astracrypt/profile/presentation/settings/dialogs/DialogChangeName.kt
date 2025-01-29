package io.gromif.astracrypt.profile.presentation.settings.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default

@Preview(showBackground = true)
@Composable
internal fun dialogChangeName(
    maxLength: Int = 30,
    name: String = "User",
    onResult: (String) -> Unit = {}
) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.settings_changeName),
    params = DialogsCore.TextFields.Params(
        text = name,
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = maxLength,
    ),
    onResult = onResult
)