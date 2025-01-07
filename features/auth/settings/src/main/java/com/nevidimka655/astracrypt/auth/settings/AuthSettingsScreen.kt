package com.nevidimka655.astracrypt.auth.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nevidimka655.astracrypt.auth.domain.SkinType
import com.nevidimka655.astracrypt.resources.R

@Composable
fun AuthSettingsScreen() {
    val vm: AuthSettingsViewModel = hiltViewModel()
    val auth by vm.authState.collectAsState()

    val typeIndex = remember(auth.type) {
        auth.type?.let { it.ordinal + 1 } ?: 0
    }
    val skinTypeIndex = remember(auth.skinType) {
        auth.skinType?.let { it.ordinal + 1 } ?: 0
    }
    SettingsAuthScreen(
        isAuthEnabled = auth.type != null,
        isAssociatedDataEncrypted = auth.bindTinkAd,
        hintState = auth.hintState,
        hintText = auth.hintText ?: stringResource(R.string.none),
        skinIndex = skinTypeIndex,
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