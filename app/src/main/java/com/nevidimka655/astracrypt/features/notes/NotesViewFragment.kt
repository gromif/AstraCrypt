package com.nevidimka655.astracrypt.features.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.OverviewScreen
import kotlinx.coroutines.launch

class NotesViewFragment : Fragment() {
    private val notesManager: NotesManager get() = TODO()

    private fun setupMenu() = (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.notes_view, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.delete -> lifecycleScope.launch {
                    notesManager.delete(notesManager.noteId)
                }.invokeOnCompletion { findNavController().popBackStack() }
                else -> return false
            }
            return true
        }
    }, viewLifecycleOwner, Lifecycle.State.STARTED)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        /*requireMainActivity().fabLarge.setOnClickListener {
            lifecycleScope.launch {
                notesManager.save(vm.aeadInfo)
                findNavController().popBackStack()
            }
        }*/
    }

}