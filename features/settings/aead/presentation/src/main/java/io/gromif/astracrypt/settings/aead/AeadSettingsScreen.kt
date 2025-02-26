package io.gromif.astracrypt.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastMap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R
import io.gromif.crypto.tink.extensions.getTemplateList
import io.gromif.crypto.tink.model.KeysetTemplates

@Composable
fun AeadSettingsScreen(
    toDatabaseColumnsAeadSettings: () -> Unit,
) {
    val vm: AeadViewModel = hiltViewModel()
    val settingsAeadNameState by vm.settingsAeadNameState.collectAsStateWithLifecycle()

    val aeadTemplateList = remember { KeysetTemplates.AEAD.getTemplateList() }

    val withoutEncryption = stringResource(R.string.withoutEncryption)
    val aeadList = remember {
        listOf(withoutEncryption) + aeadTemplateList.fastMap { it.name }
    }

    Screen(
        aeadTemplatesList = aeadList,
        toDatabaseColumnsAeadSettings = toDatabaseColumnsAeadSettings,
        settingsAeadName = settingsAeadNameState,
        onSettingsAeadChange = {
            val aeadIndex = it - 1
            val aeadId = aeadTemplateList[aeadIndex].id
            vm.setSettingsAead(id = aeadId)
        }
    )
}