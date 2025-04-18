package io.gromif.astracrypt.auth.presentation.settings.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.presentation.settings.dialogs.dialogCheckPassword
import io.gromif.astracrypt.auth.presentation.settings.dialogs.dialogPassword
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

@Composable
internal fun AuthMethodsPreference(
    currentAuthType: AuthType?,
    setPassword: (String) -> Unit,
    onDisableAuth: () -> Unit,
    onVerifyPassword: suspend (String) -> Boolean
) {
    val options = getAuthOptionsList()
    val currentOptionIndex = remember(currentAuthType) {
        currentAuthType?.let { it.ordinal + 1 } ?: 0
    }

    var dialogPasswordCheckDisableAuth by dialogCheckPassword(
        onVerify = onVerifyPassword,
        onMatch = { onDisableAuth() }
    )
    var dialogPasswordSetup by dialogPassword(onResult = setPassword)
    var dialogAuthMethodsState by DialogsCore.Selectable.radio(
        onSelected = {
            if (it != currentOptionIndex) when (it) {
                0 -> dialogPasswordCheckDisableAuth = true
                1 -> dialogPasswordSetup = true
            }
        },
        title = stringResource(id = R.string.settings_authentication),
        items = options,
        selectedItemIndex = currentOptionIndex
    )

    Preference(
        titleText = stringResource(id = R.string.settings_authenticationMethod),
        summaryText = options[currentOptionIndex]
    ) { dialogAuthMethodsState = true }
}

@Composable
private fun getAuthOptionsList(): List<String> {
    val context = LocalContext.current

    return remember {
        listOf(
            context.getString(R.string.withoutAuthentication),
            context.getString(R.string.password)
        )
    }
}