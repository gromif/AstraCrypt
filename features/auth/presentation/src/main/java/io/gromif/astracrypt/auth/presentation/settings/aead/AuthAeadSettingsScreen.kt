package io.gromif.astracrypt.auth.presentation.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastMap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.resources.R
import io.gromif.crypto.tink.core.extensions.getTemplateList
import io.gromif.crypto.tink.keyset.KeysetTemplates

@Composable
fun AuthAeadSettingsScreen() {
    val vm: AeadSettingsViewModel = hiltViewModel()
    val aeadMode by vm.aeadModeFlow.collectAsStateWithLifecycle()

    val aeadTemplateList = remember { KeysetTemplates.AEAD.getTemplateList() }

    val withoutEncryption = stringResource(R.string.withoutEncryption)
    val aeadList = remember {
        listOf(withoutEncryption) + aeadTemplateList.fastMap { it.name }
    }

    val settingsOptionIndex = remember(aeadMode) {
        if (aeadMode is AeadMode.Template) {
            aeadTemplateList.indexOfFirst { it.id == aeadMode.id } + 1
        } else 0
    }

    AeadSettingsScreen(
        params = AeadSettingsScreenParams(
            settingsOptions = aeadList,
            settingsOptionIndex = settingsOptionIndex
        ),
        actions = object : AeadSettingsScreenActions {
            override fun onSettingsChanged(optionIndex: Int) {
                val newAeadMode = if (optionIndex == 0) AeadMode.None else {
                    val template = aeadTemplateList[optionIndex - 1]
                    AeadMode.Template(id = template.id)
                }
                vm.setSettingsAead(newAeadMode)
            }
        }
    )
}