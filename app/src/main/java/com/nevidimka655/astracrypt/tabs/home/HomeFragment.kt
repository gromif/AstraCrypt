package com.nevidimka655.astracrypt.tabs.home

import android.os.Bundle
import android.view.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.ui.dialogs.ExportDialog
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.extensions.requireMenuHost

class HomeFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
        )
        setContent {
            AstraCryptTheme {
                HomeScreen(
                    vm = vm
                ) { openItem(it) }
            }
        }
    }

    private fun openItem(item: StorageItemListTuple) {
        if (item.isFile) {
            vm.openManager.reset()
            ExportDialog().show(childFragmentManager, null)
            vm.openWithDialog(itemId = item.id)
        } else {
            vm.openDirectory(
                id = item.id,
                dirName = item.name,
                popBackStack = true
            )
            vm.triggerFilesListUpdate()
            val menuItem = requireActivity().findViewById<BottomNavigationView>(
                R.id.bottomNavigationView
            ).menu.findItem(R.id.filesFragment)
            NavigationUI.onNavDestinationSelected(menuItem, findNavController())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    private fun setupMenu() = requireMenuHost().addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.home, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.lab -> findNavController().navigate(R.id.action_homeFragment_to_labFragment)
                R.id.notes -> findNavController().navigate(R.id.action_homeFragment_to_notesGraph)
                else -> return false
            }
            return true
        }
    }, viewLifecycleOwner, Lifecycle.State.STARTED)

    override fun onResume() {
        super.onResume()
        vm.loadProfileInfo()
    }
}