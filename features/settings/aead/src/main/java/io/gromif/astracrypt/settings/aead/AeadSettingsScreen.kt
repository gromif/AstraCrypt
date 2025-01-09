package io.gromif.astracrypt.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AeadSettingsScreen() {
    val vm: AeadViewModel = hiltViewModel()

    val aeadTemplatesList = remember { vm.aeadTemplateList.map { it.name } }
    val aeadLargeStreamTemplateList = remember { vm.aeadLargeStreamTemplateList.map { it.name } }
    val aeadSmallStreamTemplateList = remember { vm.aeadSmallStreamTemplateList.map { it.name } }

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