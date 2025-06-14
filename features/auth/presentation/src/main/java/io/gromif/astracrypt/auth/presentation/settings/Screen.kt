package io.gromif.astracrypt.auth.presentation.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.auth.presentation.settings.model.Actions
import io.gromif.astracrypt.auth.presentation.settings.model.Params
import io.gromif.astracrypt.auth.presentation.settings.preferences.AuthMethodsPreference
import io.gromif.astracrypt.auth.presentation.settings.preferences.BindWithEncryptionPreference
import io.gromif.astracrypt.auth.presentation.settings.preferences.CamouflagePreference
import io.gromif.astracrypt.auth.presentation.settings.preferences.HintEditorPreference
import io.gromif.astracrypt.auth.presentation.settings.preferences.HintStatePreference
import io.gromif.astracrypt.auth.presentation.settings.preferences.TimeoutPreference
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesGroupAnimated
import io.gromif.ui.compose.core.PreferencesScreen

@Preview
@Composable
internal fun SettingsAuthScreen(
    params: Params = Params(),
    actions: Actions = Actions.default
) = PreferencesScreen {
    PreferencesGroup(text = stringResource(id = R.string.settings_authentication)) {
        AuthMethodsPreference(
            currentAuthType = params.type,
            setPassword = actions::changePassword,
            onDisableAuth = actions::disableAuth,
            onVerifyPassword = actions::verifyPassword
        )
        AnimatedVisibility(params.isAuthEnabled) {
            TimeoutPreference(
                currentTimeout = params.timeout,
                onSetTimeout = actions::setTimeout
            )
        }
    }
    PreferencesGroupAnimated(
        text = stringResource(id = R.string.hint),
        isVisible = params.isAuthEnabled
    ) {
        HintStatePreference(
            isEnabled = params.hintState,
            onStateChange = actions::setHintState
        )
        AnimatedVisibility(visible = params.hintState) {
            HintEditorPreference(
                text = params.hintText,
                onSetText = actions::setHintText
            )
        }
    }
    PreferencesGroupAnimated(
        text = stringResource(id = R.string.settings_encryption),
        isVisible = params.isAuthEnabled
    ) {
        BindWithEncryptionPreference(
            isEnabled = params.isAssociatedDataEncrypted,
            onSetState = actions::setBindAuthState,
            onVerifyPassword = actions::verifyPassword
        )
    }
    PreferencesGroup(text = stringResource(id = R.string.settings_camouflage)) {
        CamouflagePreference(
            currentSkinType = params.skin,
            onDisableCamouflage = actions::disableSkin,
            onSetCalculatorCamouflage = actions::setCalculatorSkin
        )
    }
}
