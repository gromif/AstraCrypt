package io.gromif.astracrypt.files.settings.aead.columns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.ui.compose.core.ext.FlowObserver
import kotlinx.coroutines.flow.Flow

@Composable
fun FilesColumnsAeadSettingsScreen(
    setColumnsAeadEventFlow: Flow<Unit>,
) {
    val vm: ColumnsAeadSettingsViewModel = hiltViewModel()
    val aeadInfoState by vm.aeadInfoState.collectAsStateWithLifecycle()

    LaunchedEffect(aeadInfoState) {
        vm.name = aeadInfoState.name
        vm.preview = aeadInfoState.preview
        vm.file = aeadInfoState.file
        vm.flag = aeadInfoState.flag
    }

    FlowObserver(setColumnsAeadEventFlow) {
        vm.setColumnsAeadSettings()
    }

    ColumnsAeadSettingsScreen(
        params = Params(
            name = vm.name,
            preview = vm.preview,
            file = vm.file,
            flag = vm.flag
        ),
        onSetName = { vm.name = it },
        onSetPreview = { vm.preview = it },
        onSetFile = { vm.file = it },
        onSetFlag = { vm.flag = it }
    )
}
