package io.gromif.astracrypt.auth.presentation.settings.dialogs

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.default
import kotlinx.coroutines.launch

@Composable
internal fun dialogCheckPassword(
    onVerify: suspend (String) -> Boolean,
    onMatch: (phrase: String) -> Unit
): MutableState<Boolean> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    return dialogPassword {
        scope.launch {
            val passwordValidationResult = onVerify(it)
            if (passwordValidationResult) onMatch(it)
            else Toast.makeText(
                context, context.getString(R.string.t_invalidPass), Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
internal fun dialogPassword(onResult: (String) -> Unit) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.password),
    params = DialogsCore.TextFields.Params(
        label = stringResource(id = R.string.password),
        //maxLength = AppConfig.AUTH_PASSWORD_MAX_LENGTH,
        singleLine = true
    ),
    onResult = onResult
)