package com.nevidimka655.astracrypt.view.composables.lab.tink

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.tink_lab.ui.TinkLabKeyScreen
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
    val vm: TinkLabKeyViewModel = hiltViewModel()

    val context = LocalContext.current
    val dataTypes = remember { vm.buildDataTypesList(context = context) }
    var selectedDataTypeIndex by rememberSaveable { mutableIntStateOf(0) }
    var keysetPassword by rememberSaveable { mutableStateOf("") }
    var aeadType by rememberSaveable { mutableStateOf("") }
    val keyset by vm.keyState.collectAsStateWithLifecycle()

    LaunchedEffect(keysetPassword, aeadType) {
        if (aeadType.isNotEmpty()) vm.shuffleKeyset(
            keysetPassword = keysetPassword,
            dataType = dataTypes[selectedDataTypeIndex].type,
            aeadType = aeadType
        )
    }

    TinkLabKeyScreen(
        keysetHash = keyset.hash.take(16),
        aeadTypeLabel = stringResource(id = R.string.encryption_type),
        dataTypeLabel = stringResource(id = R.string.lab_dataType),
        keysetKeyLabel = stringResource(id = R.string.lab_keySetPassword),
        dataTypes = dataTypes,
        selectedDataType = dataTypes[selectedDataTypeIndex],
        onSelectDataType = { selectedDataTypeIndex = it },
        onSelectAeadType = { aeadType = it },
        keysetKey = keysetPassword,
        onChangeKeysetKey = { keysetPassword = it },
        buttonLoadText = stringResource(id = R.string.load),
        buttonSaveText = stringResource(id = R.string.save)
    )
}