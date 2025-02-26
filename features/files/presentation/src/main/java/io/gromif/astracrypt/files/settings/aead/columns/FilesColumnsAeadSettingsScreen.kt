package io.gromif.astracrypt.files.settings.aead.columns

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nevidimka655.ui.compose_core.ext.FlowObserver
import kotlinx.coroutines.flow.Flow

@Composable
fun FilesColumnsAeadSettingsScreen(
    setColumnsAeadEventFlow: Flow<Unit>,
) {
    val vm: ColumnsAeadSettingsViewModel = hiltViewModel()

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