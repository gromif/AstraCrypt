package com.nevidimka655.astracrypt.tabs.files

import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.extensions.requireMenuHost

class FilesFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    private val selectorManager get() = vm.selectorManager
    private val isStarredFragment by lazy {
        findNavController().currentDestination!!.id == R.id.starredFragment
    }

    private var searchView: SearchView? = null

    private var addStorageItemBottomSheetDialog: BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                /*FilesScreen(
                    onOptions = { onOptions(item = it) },
                    onNavigatorClick = { vm.openDirectoryFromSelector(it) },
                    onClick = {
                        when {
                            selectorManager.itemsMapState.isEmpty() -> openItem(it)
                            selectorManager.blockItems -> {
                                val isNotSelected = !selectorManager.getItemState(it.id)
                                if (isNotSelected && it.isDirectory) openItem(it)
                            }

                            else -> initSelecting(it)
                        }
                    }
                ) { if (!selectorManager.blockItems) initSelecting(it) }*/
            }
        }
    }

    private fun initSelecting(item: StorageItemListTuple) = with(selectorManager) {
        if (!isInitialized) setupContextualActionMode()
        init()
        setItemState(item.id, !selectorManager.getItemState(item.id))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        if (selectorManager.isInitialized) {
//            fab.hide()
            with(selectorManager) {
                setupContextualActionMode()
                init()
            }
        }
        setupBackCallback()
    }



    /*private fun onOptions(item: StorageItemListTuple) {
        if (selectorManager.isInitialized) return
        optionsBinding.select.setOnClickListener {
            optionsBottomSheetDialog.cancel()
            initSelecting(item)
        }
    }*/

    private fun setupContextualActionMode() {
        val actionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(
                    if (isStarredFragment) R.menu.starred_action else R.menu.files_action,
                    menu
                )
                vm.cacheUiState()
                vm.setUiState(
                    UiState(
                        navBarEnabled = false,
                        movePanelButtonState = selectorManager.blockItems,
                        fabState = false
                    )
                )
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.run {
                    title = getString(R.string.toolbar_selected, selectorManager.itemsMapState.size)
                    if (selectorManager.blockItems) {
                        subtitle = getString(R.string.toolbar_selectDestination)
                        if (menu != null) with(menu) {
                            findItem(R.id.createDir).isVisible = true
                            findItem(R.id.delete).isVisible = false
                            findItem(R.id.move).isVisible = false
                            findItem(R.id.addToStarred).isVisible = false
                        }
                    } else subtitle = null
                }
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item != null) when (item.itemId) {
                    R.id.move -> {
                        if (vm.isSearchActive()) {
                            vm.invalidateCachedUiState()
//                            closeSearchView()
                        }
                        vm.cacheUiState()
                        selectorManager.blockItems = true
                        mode?.invalidate()
                        vm.setUiState(
                            UiState(
                                navBarEnabled = false,
                                movePanelButtonState = true
                            )
                        )
                    }

                    R.id.createDir -> {/*vm.dialogNewFolderState.value = true*/}

                    R.id.delete -> {
                        vm.deleteSelected(selectorManager.getSelectedItemsList())
                        if (searchView?.isIconified != false) selectorManager.closeActionMode()
                    }
                }
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                vm.setUiState(vm.restoreCachedUiState())
                with(selectorManager) {
                    clear()
                    clearViews()
                }
            }
        }
        /*selectorManager.setView(
            viewInstance = requireToolbar(R.id.toolbar),
            actionModeInstance = actionModeCallback
        )*/
    }

    private fun setupBackCallback() {
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

//    private fun closeSearchView() = requireToolbar(R.id.toolbar).collapseActionView()

    private fun setupMenu() = requireMenuHost().addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.files, menu)
            val searchMenuItem = menu.findItem(R.id.search)

            val isSearchSupported = with(vm.encryptionInfo) {
                (!isDatabaseEncrypted || !isNameEncrypted)
            }
            searchMenuItem.isVisible = isSearchSupported
            if (!isSearchSupported) return
            searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                    adaptUiToSearch(true)
                    vm.setSearchIsEnabled(true)
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    adaptUiToSearch(false)
                    vm.setSearchIsEnabled(false)
                    return true
                }
            })
            searchView = (searchMenuItem.actionView as SearchView).apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (!query.isNullOrEmpty() && query.isNotBlank()) {
                            searchView?.run { clearFocus() }
                            vm.searchQuerySubmit(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (!newText.isNullOrEmpty() && newText.isNotBlank()) {
                            vm.searchQuerySubmit(newText)
                        }
                        return true
                    }
                })
                queryHint = getString(com.google.android.material.R.string.abc_search_hint)
            }
            if (vm.isSearchActive()) {
                searchMenuItem.expandActionView()
                searchView?.run {
                    setQuery(vm.lastSearchQuery ?: "", false)
                    clearFocus()
                }
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
            R.id.search -> true
            else -> false
        }
    }, viewLifecycleOwner, Lifecycle.State.STARTED)

    private fun adaptUiToSearch(state: Boolean) {
        vm.setUiState(
            vm.getUiState().copy(
                navBarEnabled = !state,
                fabState = if (!isStarredFragment) !state else false
            )
        )
        vm.isSearchExpandedState = state
    }

    override fun onResume() {
        super.onResume()
        if (vm.lastSearchQuery != null) searchView?.clearFocus()
    }

    override fun onDestroyView() {
        addStorageItemBottomSheetDialog = null
        searchView = null
        selectorManager.clearViews()
        super.onDestroyView()
    }
}