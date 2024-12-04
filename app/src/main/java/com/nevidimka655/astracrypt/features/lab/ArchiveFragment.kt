package com.nevidimka655.astracrypt.features.lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.help.HelpFragment
import com.nevidimka655.astracrypt.features.help.HelpIndex
import com.nevidimka655.astracrypt.ui.shared.BaseNoItemsPage
import com.nevidimka655.astracrypt.ui.shared.NoItemsPageSize
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.extensions.requireMenuHost
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.isCompact
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.launch

class ArchiveFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()
    private val labCombineZipManager get() = vm.toolsManager.labCombineZipManager

    private val addFilesContract = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) {
        //lifecycleScope.launch { labCombineZipManager.addFiles(it) }
    }
    private val addSourceFileContract = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) {
        //if (it != null) lifecycleScope.launch { labCombineZipManager.setSourceFile(it) }
    }
    private val createDocument = registerForActivityResult(
        ActivityResultContracts.CreateDocument("image/*")
    ) {
        if (it != null) lifecycleScope.launch { labCombineZipManager.start(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                ArchiveScreen()
            }
        }
    }

    @Composable
    fun ArchiveScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(rememberNestedScrollInteropConnection())
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spaces.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
        ) {
            if (LocalWindowWidth.current.isCompact) {
                LeftPanel()
                RightPanel()
            } else Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
            ) {
                Row(modifier = Modifier.weight(0.5f)) {
                    LeftPanel()
                }
                Row(modifier = Modifier.weight(0.5f)) {
                    RightPanel()
                }
            }
        }
    }

    @Preview
    @Composable
    fun LeftPanel() {
        val sourcesList = labCombineZipManager.mutableListOfSources
        InteractiveFileListPicker(
            titleText = stringResource(id = R.string.photo),
            stateList = sourcesList,
            addButtonEnabled = sourcesList.isEmpty(),
            onAddClick = { addSourceFileContract.launch(arrayOf("image/*")) },
            onClearClick = { labCombineZipManager.clearSources() }
        )
    }

    @Preview
    @Composable
    fun RightPanel() {
        val filesList = labCombineZipManager.mutableListOfFiles
        InteractiveFileListPicker(
            titleText = stringResource(id = R.string.files),
            stateList = filesList,
            onAddClick = { addFilesContract.launch(arrayOf("*/*")) },
            onClearClick = { labCombineZipManager.clearFiles() }
        )
    }

    @Preview
    @Composable
    fun InteractiveFileListPicker(
        modifier: Modifier = Modifier,
        titleText: String = "",
        stateList: SnapshotStateList<CombineZipFile> = mutableStateListOf(),
        addButtonEnabled: Boolean = true,
        onAddClick: () -> Unit = {},
        onClearClick: () -> Unit = {}
    ) {
        CardWithTitle(modifier = modifier.height(350.dp), titleText = titleText) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.size(ButtonDefaults.MinHeight),
                    onClick = onClearClick
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Clear),
                        contentDescription = stringResource(id = R.string.add)
                    )
                }
                Button(onClick = onAddClick, enabled = addButtonEnabled) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        painter = rememberVectorPainter(image = Icons.Default.Add),
                        contentDescription = stringResource(id = R.string.add)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.add))
                }
            }
            if (stateList.isNotEmpty()) LazyColumn {
                itemsIndexed(stateList) { index, it ->
                    /*FilesListItemMedium(
                        name = it.name,
                        isBackgroundTransparent = true,
                        onLongClick = {
                            stateList.removeAt(index)
                        }
                    )*/
                }
            } else BaseNoItemsPage(
                modifier = Modifier.fillMaxSize(),
                pageSize = NoItemsPageSize.MEDIUM
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireMenuHost().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.combined_zip, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.help -> findNavController().navigate(
                        R.id.action_global_helpFragment, args = bundleOf(
                            Pair(
                                HelpFragment.ARGS_HELP_INDEX_NAME,
                                HelpIndex.LAB_COMBINED_ZIP.ordinal
                            )
                        )
                    )
                    else -> return false
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
        /*requireMainActivity().fabLarge.setOnClickListener {
            if (labCombineZipManager.mutableListOfSources.isNotEmpty()) {
                createDocument.launch(labCombineZipManager.mutableListOfSources[0].name)
            }
        }*/
    }

}