package io.gromif.astracrypt.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R

@Composable
fun AeadSettingsScreen() {
    val vm: AeadViewModel = hiltViewModel()
    val noEncryptionOptionText = stringResource(R.string.withoutEncryption)
    val baseOptionsList = remember { listOf(noEncryptionOptionText) }


    val settingsAeadNameState by vm.settingsAeadNameState.collectAsStateWithLifecycle()

    /*Screen(
        aeadTemplatesList = aeadTemplatesList,
        aeadLargeStreamTemplateList = aeadLargeStreamTemplateList,
        aeadSmallStreamTemplateList = aeadSmallStreamTemplateList,
        notesAeadName = notesAeadNameState,
        onNotesAeadChange = {
            val aeadIndex = it - 1
            val aeadId = vm.aeadTemplateList[aeadIndex].id
            vm.setNotesAead(id = aeadId)
        },
        filesAeadName = "files",
        onFilesAeadChange = {},
        previewAeadName = "preview",
        onPreviewAeadChange = {},
        settingsAeadName = settingsAeadNameState,
        onSettingsAeadChange = {
            val aeadIndex = it - 1
            val aeadId = vm.aeadTemplateList[aeadIndex].id
            vm.setSettingsAead(id = aeadId)
        }
    )*/
}