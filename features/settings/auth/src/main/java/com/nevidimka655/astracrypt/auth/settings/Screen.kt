package com.nevidimka655.astracrypt.auth.settings

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
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesGroupAnimated
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default
import com.nevidimka655.ui.compose_core.dialogs.radio
import kotlinx.coroutines.launch

private val authMethods = listOf(R.string.withoutAuthentication, R.string.password)

private val skinMethods = listOf(
    R.string.settings_camouflageType_no, R.string.settings_camouflageType_calculator
)

@Composable
internal fun SettingsAuthScreen(
    isAuthEnabled: Boolean,
    isAssociatedDataEncrypted: Boolean,
    hintState: Boolean,
    hintText: String,
    typeIndex: Int,
    skinIndex: Int,
    onDisableAuth: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onVerifyPassword: suspend (String) -> Boolean,
    onSetBindPassword: (state: Boolean, password: String) -> Unit,
    onDisableSkin: () -> Unit,
    onSetSkinCalculator: (String) -> Unit,
    onChangeHintState: (Boolean) -> Unit,
    onChangeHintText: (String) -> Unit
) = PreferencesScreen {
    PreferencesGroup(text = stringResource(id = R.string.settings_authentication)) {
        var dialogPasswordCheckDisableAuth by dialogCheckPassword(
            onVerify = onVerifyPassword,
            onMatch = onDisableAuth
        )
        var dialogPasswordSetup by dialogPassword { onChangePassword(it) }
        var dialogAuthMethodsState by DialogsCore.Selectable.radio(
            onSelected = {
                if (it != typeIndex) {
                    when (it) {
                        0 -> dialogPasswordCheckDisableAuth = true
                        1 -> dialogPasswordSetup = true
                    }
                }
            },
            title = stringResource(id = R.string.settings_authentication),
            items = authMethods.map { stringResource(it) },
            selectedItemIndex = typeIndex
        )
        Preference(
            titleText = stringResource(id = R.string.settings_authenticationMethod),
            summaryText = stringResource(authMethods[typeIndex])
        ) { dialogAuthMethodsState = true }
    }
    PreferencesGroupAnimated(
        text = stringResource(id = R.string.hint),
        isVisible = isAuthEnabled
    ) {
        PreferencesSwitch(
            titleText = stringResource(id = R.string.settings_showHint),
            isChecked = hintState,
            callback = onChangeHintState
        )
        AnimatedVisibility(visible = hintState) {
            var dialogHintEditor by dialogHintEditor(currentHint = hintText) {
                if (it != hintText) onChangeHintText(it)
            }
            Preference(
                titleText = stringResource(id = R.string.hint),
                summaryText = hintText
            ) { dialogHintEditor = true }
        }
    }
    PreferencesGroupAnimated(
        text = stringResource(id = R.string.settings_encryption),
        isVisible = isAuthEnabled
    ) {
        var dialogPasswordCheckBindEncryption by dialogCheckPassword(onVerify = onVerifyPassword) {
            onSetBindPassword(!isAssociatedDataEncrypted, it)
        }
        PreferencesSwitch(
            titleText = stringResource(id = R.string.settings_bindWithFiles),
            isChecked = isAssociatedDataEncrypted
        ) { dialogPasswordCheckBindEncryption = true }
    }
    PreferencesGroup(text = stringResource(id = R.string.settings_camouflage)) {
        var dialogCalculatorCombination by dialogCalculatorCombination(onSetSkinCalculator)
        var dialogSkinMethodsState by dialogCamouflageMethods(skinIndex = skinIndex) {
            if (it != skinIndex) when (it) {
                0 -> onDisableSkin()
                1 -> dialogCalculatorCombination = true
            }
        }
        Preference(
            titleText = stringResource(id = R.string.settings_camouflage),
            summaryText = stringResource(skinMethods[skinIndex])
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