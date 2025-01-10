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

    val aeadTemplatesList = remember {
        baseOptionsList + vm.aeadTemplateList.map { it.name }
    }
    val aeadLargeStreamTemplateList = remember {
        baseOptionsList + vm.aeadLargeStreamTemplateList.map { it.name }
    }
    val aeadSmallStreamTemplateList = remember {
        baseOptionsList + vm.aeadSmallStreamTemplateList.map { it.name }
    }

    val notesAeadNameState by vm.notesAeadNameState.collectAsStateWithLifecycle()

    Screen(
        aeadTemplatesList = aeadTemplatesList,
        aeadLargeStreamTemplateList = aeadLargeStreamTemplateList,
        aeadSmallStreamTemplateList = aeadSmallStreamTemplateList,
        notesAeadName = notesAeadNameState,
        onNotesAeadChange = {},
        filesAeadName = "files",
        onFilesAeadChange = {},
        previewAeadName = "preview",
        onPreviewAeadChange = {}
    )
}