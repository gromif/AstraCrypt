package com.nevidimka655.astracrypt.tabs.files

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.OptionsFilesBinding
import com.nevidimka655.astracrypt.databinding.TextInputLayoutBinding
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.CustomLayoutParams
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.astracrypt.utils.extensions.lazyFast
import com.nevidimka655.astracrypt.utils.extensions.openKeyboard
import com.nevidimka655.astracrypt.utils.extensions.requireMenuHost
import com.nevidimka655.astracrypt.utils.extensions.requireToolbar
import com.nevidimka655.astracrypt.utils.extensions.ui.requireMainActivity
import com.nevidimka655.astracrypt.utils.extensions.ui.setDrawableTop
import com.nevidimka655.astracrypt.utils.extensions.ui.setTooltip

class FilesFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    private val fab by lazyFast { requireMainActivity().fab }
    private val selectorManager get() = vm.selectorManager
    private val isStarredFragment by lazyFast {
        findNavController().currentDestination!!.id == R.id.starredFragment
    }

    private var searchView: SearchView? = null

    private var addStorageItemBottomSheetDialog: BottomSheetDialog? = null

    /*private val exportDirContract =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            if (it != null) vm.export(
                itemToExport = vm.openManager.selectedExportItem!!,
                outputUri = it
            )
        }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setupFloatingButton(fab)
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
            fab.hide()
            with(selectorManager) {
                setupContextualActionMode()
                init()
            }
        }
        setupBackCallback()
    }



    private fun onOptions(item: StorageItemListTuple) {
        if (selectorManager.isInitialized) return
        val context = requireContext()
        val optionsBottomSheetDialog = BottomSheetDialog(context)
        val viewContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = CustomLayoutParams.LINEAR_MATCH_WRAP
        }
        val optionsBinding = OptionsFilesBinding.inflate(layoutInflater).apply {
            title.setDrawableTop(item.itemType.iconView)
            title.text = item.name
        }
        optionsBinding.export.setOnClickListener {
            /*optionsBottomSheetDialog.cancel()
            if (vm.lastExportOperation?.result?.isDone != false) {
                vm.openManager.selectedExportItem = item
                exportDirContract.launch(null)
            }*/
        }
        if (item.state.isDefault) with(optionsBinding.addToStarred) {
            isVisible = true
            setOnClickListener {
                optionsBottomSheetDialog.cancel()
                vm.setStarredFlag(true, item.id)
            }
        }
        else with(optionsBinding.removeFromStarred) {
            isVisible = true
            setOnClickListener {
                optionsBottomSheetDialog.cancel()
                vm.setStarredFlag(false, item.id)
            }
        }
        optionsBinding.select.setOnClickListener {
            optionsBottomSheetDialog.cancel()
            initSelecting(item)
        }
        optionsBinding.delete.setOnClickListener {
            optionsBottomSheetDialog.cancel()
            showDeleteDialog(context, item)
        }
        viewContainer.addView(optionsBinding.root)
        optionsBottomSheetDialog.setContentView(viewContainer, CustomLayoutParams.LINEAR_MATCH_WRAP)
        optionsBottomSheetDialog.show()
    }

    private fun showDeleteDialog(
        context: Context,
        item: StorageItemListTuple,
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle("${context.getString(R.string.files_options_delete)}?")
            .setMessage("${context.getString(R.string.files_options_delete)} \"${item.name}\"?")
            .setPositiveButton(R.string.files_options_delete) { _, _ ->
                vm.delete(item.id)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun setupFloatingButton(fab: FloatingActionButton) = with(fab) {
        setOnClickListener {

        }
        setTooltip(R.string.createNew)
    }

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
                            closeSearchView()
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

                    R.id.createDir -> vm.dialogNewFolderState.value = true
                    R.id.addToStarred, R.id.removeFromStarred -> {
                        vm.setStarredFlag(
                            state = item.itemId == R.id.addToStarred,
                            itemsArr = selectorManager.getSelectedItemsList()
                        )
                        if (searchView?.isIconified != false) selectorManager.closeActionMode()
                    }

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
        selectorManager.setView(
            viewInstance = requireToolbar(R.id.toolbar),
            actionModeInstance = actionModeCallback
        )
    }

    private fun setupBackCallback() {
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

    private fun closeSearchView() = requireToolbar(R.id.toolbar).collapseActionView()

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