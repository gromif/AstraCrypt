package com.nevidimka655.astracrypt.view.composables.lab.tink

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.tink_lab.TinkLabKeyScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

@Suppress("ObjectPropertyName")
val _LabTinkUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_aeadEncryption)
    )
)

fun NavGraphBuilder.tinkKey(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.LabGraph.TinkGraph.Key> {
    onUiStateChange(_LabTinkUiState)

    TinkLabKeyScreen(
        aeadTypeString = stringResource(id = R.string.encryption_type),
        dataTypeString = stringResource(id = R.string.lab_dataType),
        keysetKeyString = stringResource(id = R.string.lab_keySetPassword),
        dataTypeFilesString = stringResource(id = R.string.files),
        dataTypeTextString = stringResource(id = R.string.text),
        buttonLoadString = stringResource(id = R.string.load),
        buttonSaveString = stringResource(id = R.string.save)
    )
}