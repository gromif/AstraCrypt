package io.gromif.astracrypt.auth.presentation.settings.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.auth.presentation.settings.dialogs.dialogCheckPassword
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesSwitch

@Composable
internal fun BindWithEncryptionPreference(
    isEnabled: Boolean,
    onSetState: (Boolean, String) -> Unit,
    onVerifyPassword: suspend (String) -> Boolean
) {
    var dialogPasswordCheckBindEncryption by dialogCheckPassword(onVerifyPassword) {
        onSetState(!isEnabled, it)
    }
    PreferencesSwitch(
        titleText = stringResource(id = R.string.settings_bindWithFiles),
        isChecked = isEnabled
    ) { dialogPasswordCheckBindEncryption = true }
}