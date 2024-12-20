package com.nevidimka655.astracrypt.features.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme

class NotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                /*val noteItems = vm.notesPagingFlow.collectAsLazyPagingItems()
                val isEmptyPageVisible = remember {
                    derivedStateOf {
                        noteItems.itemCount == 0 && noteItems.loadState.refresh !is LoadState.Loading
                    }
                }
                if (!isEmptyPageVisible.value) NotesScreen(noteItems = noteItems) else NoItemsPage(
                    mainIcon = Icons.Filled.Description,
                    actionIcon = Icons.Filled.Edit
                )*/
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