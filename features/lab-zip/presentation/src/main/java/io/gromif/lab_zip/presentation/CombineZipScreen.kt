package io.gromif.lab_zip.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.OneLineListItem
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.isCompact
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.lab_zip.domain.FileInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CombineZipScreen(
    modifier: Modifier = Modifier,
    onRequestCombiningFlow: Flow<Unit>,
    noItemsPage: @Composable () -> Unit
) {
    val vm: CombineZipViewModel = hiltViewModel()

    val sourceContract = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { if (it != null) vm.setSource(it) }
    val filesContract = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { if (it.isNotEmpty()) vm.addFiles(it) }
    val createContract = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("image/*")
    ) { if (it != null) vm.startCombinedZipWorker(targetUri = it) }

    LaunchedEffect(Unit) {
        onRequestCombiningFlow.collectLatest {
            vm.sourceState?.let { createContract.launch(it.name) }
        }
    }

    Screen(
        modifier = modifier.padding(MaterialTheme.spaces.spaceMedium),
        source = vm.sourceState,
        onAddSource = { sourceContract.launch(arrayOf("image/*")) },
        onRemoveSource = { vm.resetSource() },
        filesList = vm.filesListState,
        onAddFiles = { filesContract.launch(arrayOf("*/*")) },
        onRemoveFiles = { vm.resetFiles() },
        noItemsPage = noItemsPage
    )
}

@Preview
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    source: FileInfo? = null,
    onAddSource: () -> Unit = {},
    onRemoveSource: () -> Unit = {},
    filesList: List<FileInfo> = emptyList(),
    onAddFiles: () -> Unit = {},
    onRemoveFiles: () -> Unit = {},
    noItemsPage: @Composable () -> Unit = {}
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {

    @Composable
    fun LeftPanel() = LeftPanel(
        source = source,
        onAddSource = onAddSource,
        onRemoveSource = onRemoveSource,
        noItemsPage = noItemsPage
    )

    @Composable
    fun RightPanel() = RightPanel(
        filesList = filesList,
        onAddFiles = onAddFiles,
        onRemoveFiles = onRemoveFiles,
        noItemsPage = noItemsPage
    )

    if (LocalWindowWidth.current.isCompact) {
        LeftPanel()
        RightPanel()
    } else Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        Row(modifier = Modifier.weight(0.5f)) { LeftPanel() }
        Row(modifier = Modifier.weight(0.5f)) { RightPanel() }
    }
}

@Composable
private fun LeftPanel(
    source: FileInfo?,
    onAddSource: () -> Unit,
    onRemoveSource: () -> Unit,
    noItemsPage: @Composable () -> Unit
) = InteractiveFileListPicker(
    titleText = stringResource(id = R.string.photo),
    stateList = source?.let { listOf(it) } ?: emptyList(),
    addButtonEnabled = source == null,
    onAddClick = onAddSource,
    onClearClick = onRemoveSource,
    noItemsPage = noItemsPage
)

@Composable
private fun RightPanel(
    filesList: List<FileInfo>,
    onAddFiles: () -> Unit,
    onRemoveFiles: () -> Unit,
    noItemsPage: @Composable () -> Unit
) = InteractiveFileListPicker(
    titleText = stringResource(id = R.string.files),
    stateList = filesList,
    onAddClick = onAddFiles,
    onClearClick = onRemoveFiles,
    noItemsPage = noItemsPage
)

@Composable
private fun InteractiveFileListPicker(
    modifier: Modifier = Modifier,
    titleText: String = "",
    stateList: List<FileInfo>,
    addButtonEnabled: Boolean = true,
    onAddClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    noItemsPage: @Composable () -> Unit
) = CardWithTitle(modifier = modifier.height(350.dp), titleText = titleText) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.size(ButtonDefaults.MinHeight),
            onClick = onClearClick
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.add)
            )
        }
        Button(onClick = onAddClick, enabled = addButtonEnabled) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.add))
        }
    }
    if (stateList.isNotEmpty()) LazyColumn {
        itemsIndexed(stateList) { index, it ->
            OneLineListItem(
                titleText = it.name
            )
        }
    } else noItemsPage()
}