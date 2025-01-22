package io.gromif.astracrypt.settings.aead.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.radio

@Composable
internal fun SettingsGroup(
    aeadTemplatesList: List<String> = listOf(),
    settingsAeadName: String? = "Test Settings Aead",
    onSettingsAeadChange: (Int) -> Unit = {}
) = PreferencesGroup(text = stringResource(id = R.string.settings)) {
    val selectedIndex = remember(settingsAeadName) {
        if (settingsAeadName == null) 0 else {
            aeadTemplatesList.indexOfFirst { it == settingsAeadName }
        }
    }
    var dialogSettingsState by DialogsCore.Selectable.radio(
        onSelected = onSettingsAeadChange,
        title = stringResource(id = R.string.settings),
        items = aeadTemplatesList,
        selectedItemIndex = selectedIndex
    )
    Preference(
        titleText = stringResource(id = R.string.settings),
        summaryText = settingsAeadName ?: stringResource(R.string.withoutEncryption)
    ) {
        dialogSettingsState = true
    }
}