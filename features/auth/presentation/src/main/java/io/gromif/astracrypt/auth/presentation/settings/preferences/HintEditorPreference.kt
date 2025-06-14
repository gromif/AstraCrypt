package io.gromif.astracrypt.auth.presentation.settings.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.default

@Composable
internal fun HintEditorPreference(
    text: String,
    onSetText: (String) -> Unit
) {
    var dialogHintEditor by dialogHintEditor(currentHint = text) {
        if (it != text) onSetText(it)
    }
    Preference(
        titleText = stringResource(id = R.string.hint),
        summaryText = text
    ) { dialogHintEditor = true }
}

@Composable
private fun dialogHintEditor(
    currentHint: String,
    onResult: (String) -> Unit
) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.hint),
    params = DialogsCore.TextFields.Params(
        text = currentHint,
        label = stringResource(id = R.string.hint),
        selectAllText = true,
        // maxLength = AppConfig.AUTH_HINT_MAX_LENGTH,
    ),
    onResult = onResult
)
