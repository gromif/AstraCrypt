package io.gromif.astracrypt.auth.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.presentation.settings.model.Actions
import io.gromif.astracrypt.auth.presentation.settings.model.Params
import io.gromif.astracrypt.resources.R

@Composable
fun AuthSettingsScreen() {
    val vm: AuthSettingsViewModel = hiltViewModel()
    val placeholderValue = remember { Auth() }
    val auth by vm.authFlow.collectAsStateWithLifecycle(placeholderValue)

    if (auth !== placeholderValue) SettingsAuthScreen(
        params = Params(
            isAuthEnabled = auth.type != null,
            typeIndex = auth.type?.let { it.ordinal + 1 } ?: 0,
            skinIndex = auth.skinType?.let { it.ordinal + 1 } ?: 0,
            isAssociatedDataEncrypted = auth.bindTinkAd,
            hintState = auth.hintState,
            hintText = auth.hintText ?: stringResource(R.string.none)
        ),
        actions = object : Actions {
            override fun disableAuth() {
                vm.disable()
            }

            override fun disableSkin() {
                vm.disableSkin()
            }

            override fun changePassword(password: String) {
                vm.setPassword(password)
            }

            override suspend fun verifyPassword(password: String): Boolean {
                return vm.verifyPassword(password)
            }

            override fun setBindAuthState(state: Boolean, password: String) {
                vm.setBindAssociatedData(state, password)
            }

            override fun setCalculatorSkin(combination: String) {
                vm.setCalculatorSkin(combination)
            }

            override fun setHintState(state: Boolean) {
                vm.setHintState(state)
            }

            override fun setHintText(text: String) {
                vm.setHintText(text)
            }

        }
    )

}