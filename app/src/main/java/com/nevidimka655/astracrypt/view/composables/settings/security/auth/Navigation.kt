package com.nevidimka655.astracrypt.view.composables.settings.security.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.domain.model.auth.Auth
import com.nevidimka655.astracrypt.domain.model.auth.Skin
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val SettingsSecurityAuthUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_authentication)
    )
)

fun NavGraphBuilder.settingsSecurityAuth(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityAuth> {
    onUiStateChange(SettingsSecurityAuthUiState)
    val vm: SettingsAuthViewModel = hiltViewModel()
    val auth by vm.authInfoFlow.collectAsStateWithLifecycle(initialValue = Auth())
    val aeadInfo by vm.aeadInfoFlow.collectAsStateWithLifecycle(initialValue = AeadInfo())

    val typeIndex = remember(auth.type) {
        auth.type?.let { it.ordinal + 1 } ?: 0
    }
    val skinIndex = remember(auth.skin) {
        when (auth.skin) {
            null -> 0
            is Skin.Calculator -> 1
        }
    }
    SettingsAuthScreen(
        isAuthEnabled = auth.type != null,
        isAssociatedDataEncrypted = aeadInfo.bindAssociatedData,
        hintState = auth.hintState,
        hintText = auth.hintText ?: stringResource(R.string.none),
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