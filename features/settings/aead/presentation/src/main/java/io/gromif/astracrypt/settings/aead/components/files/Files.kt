package io.gromif.astracrypt.settings.aead.components.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.radio

@Composable
internal fun Files(
    aeadLargeStreamTemplateList: List<String> = listOf(),
    filesAeadName: String? = "Test Files SAead",
    onFilesAeadChange: (Int) -> Unit = {}
) {

    val selectedIndex = remember(filesAeadName) {
        if (filesAeadName == null) 0 else {
            aeadLargeStreamTemplateList.indexOfFirst { it == filesAeadName }
        }
    }

    var dialogFilesState by DialogsCore.Selectable.radio(
        onSelected = onFilesAeadChange,
        title = stringResource(id = R.string.files),
        items = aeadLargeStreamTemplateList,
        selectedItemIndex = selectedIndex
    )

    Preference(
        titleText = stringResource(id = R.string.files),
        summaryText = filesAeadName ?: stringResource(R.string.withoutEncryption)
    ) {
        dialogFilesState = true
    }
}