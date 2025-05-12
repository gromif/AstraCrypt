package io.gromif.tink_lab.presentation.files

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.resources.R
import io.gromif.tink_lab.presentation.TinkLab
import io.gromif.tink_lab.presentation.shared.AssociatedDataTextField
import io.gromif.tink_lab.presentation.shared.EncryptionToolbar
import io.gromif.ui.compose.core.FilledTonalIconButton
import io.gromif.ui.compose.core.ext.LocalWindowWidth
import io.gromif.ui.compose.core.ext.isCompact
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun TinkLab.FilesScreen(
    modifier: Modifier = Modifier,
    rawKeyset: String
) {
    val context = LocalContext.current
    val vm: FilesViewModel = hiltViewModel()
    val source by vm.sourceDirName.collectAsStateWithLifecycle()
    val destination by vm.destinationDirName.collectAsStateWithLifecycle()

    val inputContract = FilesContracts.openInput { vm.setSource(context, it) }
    val outputDirContract = FilesContracts.openOutputDir { vm.setDestinationDir(context, it) }

    Screen(
        modifier = modifier,
        state = FilesScreenState(
            associatedData = vm.associatedData,
            source = source,
            destination = destination,
            processingState = vm.isWorkerActive
        ),
        onEncrypt = { vm.startFilesWorker(rawKeyset = rawKeyset, true) },
        onDecrypt = { vm.startFilesWorker(rawKeyset = rawKeyset, false) },
        onAssociatedDataChange = vm::associatedData::set,
        onSourceClick = { inputContract.launch(arrayOf("*/*")) },
        onDestinationClick = { outputDirContract.launch(null) },
    )
}

@Preview(showBackground = true)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    state: FilesScreenState = FilesScreenState(),
    onEncrypt: () -> Unit = {},
    onDecrypt: () -> Unit = {},
    onAssociatedDataChange: (String) -> Unit = {},
    onSourceClick: () -> Unit = {},
    onDestinationClick: () -> Unit = {},
) = Column(
    modifier = modifier.padding(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    val localWindowWidth = LocalWindowWidth.current
    if (localWindowWidth.isCompact) {
        EncryptionToolbar(onEncrypt = onEncrypt, onDecrypt = onDecrypt)
        AssociatedDataTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.associatedData,
            onValueChange = onAssociatedDataChange
        )
        SourceTextField(value = state.source, onSourceClick = onSourceClick)
        DestinationTextField(value = state.destination, onDestinationClick = onDestinationClick)
        DetailsCard(state = state.processingState)
    } else Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
        ) {
            EncryptionToolbar(onEncrypt = onEncrypt, onDecrypt = onDecrypt)
            DetailsCard(state = state.processingState)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
        ) {
            AssociatedDataTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.associatedData,
                onValueChange = onAssociatedDataChange
            )
            SourceTextField(value = state.source, onSourceClick = onSourceClick)
            DestinationTextField(value = state.destination, onDestinationClick = onDestinationClick)
        }
    }
}

@Composable
private fun SourceTextField(
    value: String,
    onSourceClick: () -> Unit
) = OutlinedTextField(
    value = value,
    onValueChange = {},
    label = { Text(text = stringResource(id = R.string.source)) },
    leadingIcon = {
        Icon(imageVector = Icons.Outlined.Source, stringResource(R.string.add))
    },
    trailingIcon = {
        FilledTonalIconButton(icon = Icons.Outlined.FolderOpen, onClick = onSourceClick)
    },
    readOnly = true,
    singleLine = true,
    modifier = Modifier.fillMaxWidth()
)

@Composable
private fun DestinationTextField(
    value: String,
    onDestinationClick: () -> Unit
) = OutlinedTextField(
    value = value,
    onValueChange = {},
    label = { Text(text = stringResource(id = R.string.destination)) },
    leadingIcon = {
        Icon(imageVector = Icons.Default.Output, null)
    },
    trailingIcon = {
        FilledTonalIconButton(icon = Icons.Outlined.FolderOpen, onClick = onDestinationClick)
    },
    readOnly = true,
    singleLine = true,
    modifier = Modifier.fillMaxWidth()
)

@Composable
private fun DetailsCard(state: Boolean) = AnimatedVisibility(
    visible = state,
    enter = expandVertically() + fadeIn(),
    exit = shrinkVertically() + fadeOut()
) {
    OutlinedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spaces.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spaces.spaceMedium
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.dialog_exporting),
                fontWeight = FontWeight.SemiBold
            )
            LinearProgressIndicator()
        }
    }
}