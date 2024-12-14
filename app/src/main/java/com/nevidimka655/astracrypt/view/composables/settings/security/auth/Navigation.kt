package com.nevidimka655.astracrypt.view.composables.settings.security.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.features.auth.model.AuthInfo
import com.nevidimka655.astracrypt.features.auth.model.Skin
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsSecurityAuthUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_authentication)
    )
)

inline fun NavGraphBuilder.settingsSecurityAuth(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityAuth> {
    onUiStateChange(SettingsSecurityAuthUiState)
    val vm: SettingsAuthViewModel = hiltViewModel()
    val authInfo by vm.authInfoFlow.collectAsStateWithLifecycle(initialValue = AuthInfo())
    val aeadInfo by vm.aeadInfoFlow.collectAsStateWithLifecycle(initialValue = AeadInfo())

    val typeIndex = remember(authInfo.type) {
        authInfo.type?.let { it.ordinal + 1 } ?: 0
    }
    val skinIndex = remember(authInfo.skin) {
        when (authInfo.skin) {
            null -> 0
            is Skin.Calculator -> 1
        }
    }
    SettingsAuthScreen(
        isAuthEnabled = authInfo.type != null,
        isAssociatedDataEncrypted = aeadInfo.bindAssociatedData,
        hintState = authInfo.hintState,
        hintText = authInfo.hintText ?: stringResource(R.string.none),
        skinIndex = skinIndex,
        typeIndex = typeIndex,
        onDisableAuth = {
            vm.setBindAssociatedData(state = false, password = it)
            vm.disable()
        },
        onChangePassword = { vm.setPassword(password = it) },
        onVerifyPassword = { vm.verifyPassword(password = it) },
        onSetBindPassword = { state, password -> vm.setBindAssociatedData(state, password) },
        onDisableSkin = { vm.disableSkin() },
        onSetSkinCalculator = { vm.setCalculatorSkin(combination = it) },
        onChangeHintState = { vm.setHintState(state = it) },
        onChangeHintText = { vm.setHintText(text = it) }
    )
}