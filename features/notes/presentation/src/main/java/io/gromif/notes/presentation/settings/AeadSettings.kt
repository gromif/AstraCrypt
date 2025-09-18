package io.gromif.notes.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AeadSettings(modifier: Modifier = Modifier) {
    val vm: AeadSettingsViewModel = hiltViewModel()
    val aeadModeState by vm.aeadPreference.collectAsStateWithLifecycle()

    AeadSettingsUi(
        modifier = modifier,
        aeadMode = aeadModeState,
        aeadTemplatesList = vm.aeadTemplatesList,
        onNotesAeadChange = vm::setAeadPreference,
    )
}