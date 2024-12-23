package com.nevidimka655.astracrypt.view

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.data.database.PagerTuple

class FilesFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    private val selectorManager get() = vm.selectorManager

    private fun initSelecting(item: PagerTuple) = with(selectorManager) {
        if (!isInitialized) setupContextualActionMode()
        init()
        setItemState(item.id, !getItemState(item.id))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (selectorManager.isInitialized) {
//            fab.hide()
            with(selectorManager) {
                setupContextualActionMode()
                init()
            }
        }
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
                /*vm.cacheUiState()
                vm.setUiState(
                    UiStateOld(
                        navBarEnabled = false,
                        movePanelButtonState = selectorManager.blockItems,
                        fabState = false
                    )
                )*/
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
                        vm.cacheUiState()
                        selectorManager.blockItems = true
                        mode?.invalidate()
                        /*vm.setUiState(
                            UiStateOld(
                                navBarEnabled = false,
                                movePanelButtonState = true
                            )
                        )*/
                    }

                    R.id.createDir -> {/*vm.dialogNewFolderState.value = true*/}

                    R.id.delete -> {
                        vm.deleteSelected(selectorManager.getSelectedItemsList())
                    }
                }
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                /*vm.setUiState(vm.restoreCachedUiState())*/
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

}