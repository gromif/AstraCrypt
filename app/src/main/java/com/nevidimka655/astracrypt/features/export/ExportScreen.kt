package com.nevidimka655.astracrypt.features.export

import android.content.Intent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DriveFolderUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.model.ExportUiState
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.ui.compose_core.theme.spaces

@Preview(showBackground = true)
@Composable
fun ExportScreen(
    vm: ExportScreenViewModel = viewModel(),
    state: ExportUiState = vm.uiState,
    encryptionInfo: EncryptionInfo = EncryptionInfo(),
    itemId: Long = 0
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(
        MaterialTheme.spaces.spaceMedium,
        Alignment.CenterVertically
    ),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val context = LocalContext.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    DisposableEffect(Unit) {
        vm.openWithDialog(encryptionInfo = encryptionInfo, itemId = itemId)
        onDispose { IO.clearExportedCache() }
    }
    val isDecrypted = remember(state) { state.progress == state.itemsCount }
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
            AnimatedVisibility(visible = !isDecrypted) {
                LinearProgressIndicator(modifier = Modifier.width(96.dp))
            }
        }
    }
    Text(
        modifier = Modifier.padding(30.dp),
        text = state.name,
        style = MaterialTheme.typography.titleMedium
    )
    Button(enabled = isDecrypted, onClick = {
        val intentView = Intent(Intent.ACTION_VIEW).apply {
            data = state.lastOutputFile
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        try {
            context.startActivity(intentView)
        } catch (_: Exception) {
        }
    }) {
        Text(text = stringResource(id = R.string.open))
    }
    OutlinedButton(onClick = { onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed() }) {
        Text(text = stringResource(id = android.R.string.cancel))
    }
}