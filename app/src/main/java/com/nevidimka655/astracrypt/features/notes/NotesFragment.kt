package com.nevidimka655.astracrypt.features.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.shared.NoItemsPage
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.extensions.ui.requireMainActivity
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.List
import com.nevidimka655.notes.ui.Note
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()
    private val notesManager get() = vm.toolsManager.notesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                val noteItems = vm.notesPagingFlow.collectAsLazyPagingItems()
                val isEmptyPageVisible = remember {
                    derivedStateOf {
                        noteItems.itemCount == 0 && noteItems.loadState.refresh !is LoadState.Loading
                    }
                }
                if (!isEmptyPageVisible.value) NotesScreen(noteItems = noteItems) else NoItemsPage(
                    mainIcon = Icons.Filled.Description,
                    actionIcon = Icons.Filled.Edit
                )
            }
        }
    }

    @Composable
    fun NotesScreen(noteItems: LazyPagingItems<Notes.Item>) = Notes.List {
        items(
            count = noteItems.itemSnapshotList.size,
            key = { noteItems[it]?.id ?: it }
        ) { index ->
            noteItems[index]?.let {
                Notes.Note(
                    title = it.title,
                    summary = it.textPreview,
                    date = it.creationTime
                ) {
                    lifecycleScope.launch {
                        notesManager.preloadText(
                            encryptionInfo = vm.encryptionInfo,
                            noteId = it.id,
                            title = it.title
                        )
                    }
                    showNote(title = it.title)
                }
            }
        }
    }

    private fun showNote(title: String? = null) {
        findNavController().navigate(
            R.id.action_notesFragment_to_notesViewFragment,
            args = bundleOf(Pair("noteName", title ?: getString(R.string.notes)))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*requireMainActivity().fabLarge.setOnClickListener {
            notesManager.reset()
            showNote()
        }*/
    }

}