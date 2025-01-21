package com.nevidimka655.astracrypt.view.composables.export

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.ui.compose_core.theme.spaces

@Preview(showBackground = true)
@Composable
fun ExportScreen(
    isExternalExport: Boolean = false,
    //state: ExportUiState = ExportUiState(),
    onStart: () -> Unit = {},
    onOpenExportedFile: () -> Unit = {},
    onCancelExport: () -> Unit = {},
    onDispose: () -> Unit = {}
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(
        space = MaterialTheme.spaces.spaceMedium,
        alignment = Alignment.CenterVertically
    ),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    fun back() = onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()

    /*DisposableEffect(Unit) {
        onStart()
        onDispose { onDispose() }
    }

    ElevatedCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(50.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.DriveFolderUpload,
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            AnimatedVisibility(visible = !state.isDone) {
                LinearProgressIndicator(modifier = Modifier.width(96.dp))
            }
        }
    }
    Text(
        modifier = Modifier.padding(30.dp),
        text = state.name,
        style = MaterialTheme.typography.titleMedium
    )
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            enabled = state.isDone,
            onClick = {
                if (isExternalExport) back()
                else onOpenExportedFile()
            }
        ) {
            Text(
                text = stringResource(
                    id = if (isExternalExport) android.R.string.ok else R.string.open
                )
            )
        }
        AnimatedVisibility(visible = !state.isDone) {
            OutlinedButton(
                onClick = {
                    if (isExternalExport) onCancelExport()
                    back()
                },
                modifier = Modifier.padding(top = MaterialTheme.spaces.spaceMedium)
            ) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        }
    }
*/}