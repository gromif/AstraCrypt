package io.gromif.astracrypt.auth.presentation.settings

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.auth.presentation.settings.model.Actions
import io.gromif.astracrypt.auth.presentation.settings.model.Params
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesGroupAnimated
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.PreferencesSwitch
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.default
import io.gromif.ui.compose.core.dialogs.radio
import kotlinx.coroutines.launch

private val authMethods = listOf(R.string.withoutAuthentication, R.string.password)

private val skinMethods = listOf(
    R.string.settings_camouflageType_no, R.string.settings_camouflageType_calculator
)

@Preview
@Composable
internal fun SettingsAuthScreen(
    params: Params = Params(),
    actions: Actions = Actions.default
) = PreferencesScreen {
    PreferencesGroup(text = stringResource(id = R.string.settings_authentication)) {
        var dialogPasswordCheckDisableAuth by dialogCheckPassword(
            onVerify = actions::verifyPassword,
            onMatch = { actions.disableAuth() }
        )
        var dialogPasswordSetup by dialogPassword(onResult = actions::changePassword)
        var dialogAuthMethodsState by DialogsCore.Selectable.radio(
            onSelected = {
                if (it != params.typeIndex) when (it) {
                    0 -> dialogPasswordCheckDisableAuth = true
                    1 -> dialogPasswordSetup = true
                }
            },
            title = stringResource(id = R.string.settings_authentication),
            items = authMethods.map { stringResource(it) },
            selectedItemIndex = params.typeIndex
        )
        Preference(
            titleText = stringResource(id = R.string.settings_authenticationMethod),
            summaryText = stringResource(authMethods[params.typeIndex])
        ) { dialogAuthMethodsState = true }
    }
    PreferencesGroupAnimated(
        text = stringResource(id = R.string.hint),
        isVisible = params.isAuthEnabled
    ) {
        PreferencesSwitch(
            titleText = stringResource(id = R.string.settings_showHint),
            isChecked = params.hintState,
            callback = actions::setHintState
        )
        AnimatedVisibility(visible = params.hintState) {
            var dialogHintEditor by dialogHintEditor(currentHint = params.hintText) {
                if (it != params.hintText) actions.setHintText(it)
            }
            Preference(
                titleText = stringResource(id = R.string.hint),
                summaryText = params.hintText
            ) { dialogHintEditor = true }
        }
    }
    PreferencesGroupAnimated(
        text = stringResource(id = R.string.settings_encryption),
        isVisible = params.isAuthEnabled
    ) {
        var dialogPasswordCheckBindEncryption by dialogCheckPassword(actions::verifyPassword) {
            actions.setBindAuthState(!params.isAssociatedDataEncrypted, it)
        }
        PreferencesSwitch(
            titleText = stringResource(id = R.string.settings_bindWithFiles),
            isChecked = params.isAssociatedDataEncrypted
        ) { dialogPasswordCheckBindEncryption = true }
    }
    PreferencesGroup(text = stringResource(id = R.string.settings_camouflage)) {
        var dialogCalculatorCombination by dialogCalculatorCombination(actions::setCalculatorSkin)
        var dialogSkinMethodsState by dialogCamouflageMethods(skinIndex = params.skinIndex) {
            if (it != params.skinIndex) when (it) {
                0 -> actions.disableSkin()
                1 -> dialogCalculatorCombination = true
            }
        }
        Preference(
            titleText = stringResource(id = R.string.settings_camouflage),
            summaryText = stringResource(skinMethods[params.skinIndex])
        ) { dialogSkinMethodsState = true }
    }
}

@Composable
private fun dialogCamouflageMethods(
    skinIndex: Int, onSelected: (Int) -> Unit
): MutableState<Boolean> = DialogsCore.Selectable.radio(
    onSelected = onSelected,
    title = stringResource(id = R.string.settings_camouflage),
    items = skinMethods.map { stringResource(it) },
    selectedItemIndex = skinIndex
)

@Composable
private fun dialogCheckPassword(
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
private fun dialogPassword(onResult: (String) -> Unit) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.password),
    params = DialogsCore.TextFields.Params(
        label = stringResource(id = R.string.password),
        //maxLength = AppConfig.AUTH_PASSWORD_MAX_LENGTH,
        singleLine = true
    ),
    onResult = onResult
)

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
        //maxLength = AppConfig.AUTH_HINT_MAX_LENGTH,
    ),
    onResult = onResult
)

@Composable
private fun dialogCalculatorCombination(
    onResult: (String) -> Unit
) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.settings_camouflage_numberCombination),
    params = DialogsCore.TextFields.Params(
        label = stringResource(id = R.string.settings_camouflage_numberCombination),
        selectAllText = true,
        //maxLength = CalculatorManager.MAX_NUM_LENGTH,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
    ),
    onResult = onResult
)