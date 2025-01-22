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
internal fun Preview(
    aeadSmallStreamTemplateList: List<String> = listOf(),
    previewAeadName: String? = "Test Preview SAead",
    onPreviewAeadChange: (Int) -> Unit = {}
) {
    val selectedIndex = remember(previewAeadName) {
        if (previewAeadName == null) 0 else {
            aeadSmallStreamTemplateList.indexOfFirst { it == previewAeadName }
        }
    }

    var dialogThumbsState by DialogsCore.Selectable.radio(
        onSelected = onPreviewAeadChange,
        title = stringResource(id = R.string.thumbnail),
        items = aeadSmallStreamTemplateList,
        selectedItemIndex = selectedIndex
    )

    Preference(
        titleText = stringResource(id = R.string.thumbnail),
        summaryText = previewAeadName ?: stringResource(R.string.withoutEncryption)
    ) {
        dialogThumbsState = true
    }
}