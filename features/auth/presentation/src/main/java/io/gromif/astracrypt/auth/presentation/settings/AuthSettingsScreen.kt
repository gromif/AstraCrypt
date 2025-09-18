package io.gromif.astracrypt.auth.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.presentation.settings.model.Actions
import io.gromif.astracrypt.auth.presentation.settings.model.Params
import io.gromif.astracrypt.resources.R

@Composable
fun AuthSettingsScreen() {
    val vm: AuthSettingsViewModel = hiltViewModel()
    val authState by vm.authFlow.collectAsStateWithLifecycle(null)
    val auth = authState

    if (auth != null) {
        SettingsAuthScreen(
            params = Params(
                isAuthEnabled = auth.type != null,
                type = auth.type,
                timeout = auth.timeout,
                skin = auth.skinType,
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

                override fun setTimeout(timeout: Timeout) {
                    vm.setTimeout(timeout)
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
}
