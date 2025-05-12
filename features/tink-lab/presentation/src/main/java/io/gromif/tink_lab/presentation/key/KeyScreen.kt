package io.gromif.tink_lab.presentation.key

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.gromif.astracrypt.resources.R
import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.Key
import io.gromif.tink_lab.presentation.TinkLab
import io.gromif.tink_lab.presentation.key.menu.AeadTypeMenu
import io.gromif.tink_lab.presentation.key.menu.DataTypeMenu
import io.gromif.tink_lab.presentation.shared.ToolbarButton
import io.gromif.ui.compose.core.TextFields
import io.gromif.ui.compose.core.ext.FlowObserver
import io.gromif.ui.compose.core.ext.LocalWindowWidth
import io.gromif.ui.compose.core.ext.isCompact
import io.gromif.ui.compose.core.text_fields.icons.PasswordToggleIconButton
import io.gromif.ui.compose.core.theme.spaces
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun TinkLab.KeyScreen(
    modifier: Modifier = Modifier,
    onRequestKeysetChannel: Flow<Unit>,
    onInvalidPassword: suspend () -> Unit,
    navigateToTextMode: (keyset: String) -> Unit,
    navigateToFilesMode: (keyset: String) -> Unit
) {
    val vm: KeyViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    var uiMode by vm.uiMode
    var aeadType by vm.aeadType
    var dataType by vm.dataType
    var keysetPasswordErrorState by remember { mutableStateOf(false) }

    fun navigate(dataType: DataType, rawKeyset: String) = when (dataType) {
        DataType.Files -> navigateToFilesMode(rawKeyset)
        DataType.Text -> navigateToTextMode(rawKeyset)
    }

    FlowObserver(onRequestKeysetChannel) {
        var key: Key? = null
        val currentUiMode = uiMode
        when (currentUiMode) {
            UiMode.CreateKey -> key = vm.createKey(dataType, aeadType)
            is UiMode.LoadKey -> {
                val loadedKey = vm.load(path = currentUiMode.keysetPath)
                if (loadedKey == null) {
                    keysetPasswordErrorState = true
                    onInvalidPassword()
                    keysetPasswordErrorState = false
                } else key = loadedKey
            }
        }
        if (key != null) navigate(key.dataType, key.rawKeyset)
    }

    val openContract = KeyContracts.open {
        uiMode = UiMode.LoadKey(keysetPath = it.toString())
    }
    val saveContract = KeyContracts.save {
        scope.launch {
            val key = vm.createKey(dataType, aeadType)
            vm.save(key = key, uri = it)
            vm.keysetPassword = ""
            navigate(key.dataType, key.rawKeyset)
        }
    }

    Screen(
        modifier = modifier,
        state = KeyScreenState(
            uiMode = uiMode,
            fileAeadList = vm.fileAeadList,
            textAeadList = vm.textAeadList,
            dataTypes = DataType.entries,
            dataType = dataType,
            aeadType = aeadType,
            keysetKey = vm.keysetPassword,
            isWrongPassword = keysetPasswordErrorState,
        ),
        onSelectDataType = { dataType = it },
        onSelectAeadType = { aeadType = it },
        onLoadClick = { openContract.launch(arrayOf("text/plain")) },
        onSaveClick = { saveContract.launch("ac_key_${abs(Random.nextInt())}.txt") },
        onChangeKeysetKey = vm::keysetPassword::set
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    state: KeyScreenState = KeyScreenState(),
    onSelectDataType: (DataType) -> Unit = {},
    onSelectAeadType: (String) -> Unit = {},
    onLoadClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onChangeKeysetKey: (String) -> Unit = {}
) = Box(
    modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    contentAlignment = Alignment.Center
) {
    val isLoadMode = remember(state.uiMode) { state.uiMode is UiMode.LoadKey }
    ElevatedCard {
        val localWindowWidth = LocalWindowWidth.current
        val defaultVerticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spaces.spaceMedium, Alignment.CenterVertically
        )
        val defaultHorizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(MaterialTheme.spaces.spaceMedium),
            verticalArrangement = defaultVerticalArrangement,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showDataTypeMenu by remember { mutableStateOf(false) }

            @Composable
            fun dataTypeMenu(modifier: Modifier = Modifier.fillMaxWidth()) = DataTypeMenu(
                modifier = modifier,
                expanded = showDataTypeMenu,
                enabled = !isLoadMode,
                text = stringResource(id = state.dataType.titleStringId()),
                label = stringResource(id = R.string.lab_dataType),
                onExpandedChange = { if (!isLoadMode) showDataTypeMenu = it },
                items = state.dataTypes,
                onSelect = onSelectDataType
            )

            var showAeadTypeMenu by remember { mutableStateOf(false) }
            val aeadTypes = remember(state.dataType) {
                when (state.dataType) {
                    DataType.Files -> state.fileAeadList
                    DataType.Text -> state.textAeadList
                }
            }
            LaunchedEffect(aeadTypes) { onSelectAeadType(aeadTypes[0]) }

            @Composable
            fun aeadTypeMenu() = AeadTypeMenu(
                expanded = showAeadTypeMenu,
                enabled = !isLoadMode,
                text = state.aeadType,
                label = stringResource(id = R.string.encryption_type),
                onExpandedChange = { if (!isLoadMode) showAeadTypeMenu = it },
                items = aeadTypes,
                onSelect = onSelectAeadType
            )

            @Composable
            fun keysetKeyField() = KeysetKeyTextField(
                value = state.keysetKey,
                onValueChange = onChangeKeysetKey,
                isError = state.isWrongPassword
            )

            @Composable
            fun toolbar(modifier: Modifier = Modifier) = Row(
                horizontalArrangement = defaultHorizontalArrangement,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                ToolbarButton(
                    imageVector = Icons.Default.Add,
                    text = stringResource(id = R.string.load),
                    modifier = Modifier.weight(0.5f),
                    onClick = onLoadClick
                )
                ToolbarButton(
                    imageVector = Icons.Default.Save,
                    text = stringResource(id = R.string.save),
                    modifier = Modifier.weight(0.5f),
                    onClick = onSaveClick
                )
            }

            if (localWindowWidth.isCompact) {
                keysetKeyField()
                dataTypeMenu()
                aeadTypeMenu()
                if (!isLoadMode) toolbar()
            } else {
                Row(horizontalArrangement = defaultHorizontalArrangement) {
                    val horizontalAlignment = Alignment.CenterHorizontally
                    Column(
                        modifier = Modifier.weight(0.5f),
                        verticalArrangement = defaultVerticalArrangement,
                        horizontalAlignment = horizontalAlignment
                    ) {
                        keysetKeyField()
                        dataTypeMenu()
                    }
                    Column(
                        modifier = Modifier.weight(0.5f),
                        verticalArrangement = defaultVerticalArrangement,
                        horizontalAlignment = horizontalAlignment
                    ) {
                        aeadTypeMenu()
                        if (!isLoadMode) toolbar(
                            modifier = Modifier.height(TextFieldDefaults.MinHeight)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KeysetKeyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = Icons.Default.Key, null) },
        supportingText = {
            Text(
                text = "${value.length}",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        },
        trailingIcon = {
            TextFields.Icons.PasswordToggleIconButton(passwordVisible) { passwordVisible = it }
        },
        placeholder = TextFields.placeholder(text = stringResource(R.string.lab_keySetPassword)),
        label = TextFields.label(text = stringResource(R.string.lab_keySetPassword)),
        visualTransformation = TextFields.passwordVisualTransform(state = passwordVisible),
        singleLine = true,
        isError = isError,
        modifier = Modifier.fillMaxWidth()
    )
}