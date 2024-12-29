package com.nevidimka655.astracrypt.view

import android.view.ActionMode
import android.view.View
import androidx.compose.runtime.mutableStateMapOf

class SelectorManager {
    val itemsMapState = mutableStateMapOf<Long, Boolean>()
    private var actionModeCallback: ActionMode.Callback? = null
    private var actionMode: ActionMode? = null
    private var view: View? = null
    var isInitialized = false
    var blockItems = false

    fun init() {
        if (!isInitialized) {
            itemsMapState.clear()
            isInitialized = true
        }
        if (actionMode == null) startActionMode()
    }

    private fun startActionMode() {
        actionMode = view?.startActionMode(actionModeCallback)
    }

    fun closeActionMode() {
        actionMode?.finish()
    }

    fun getSelectedItemsList() = itemsMapState.keys.toList()

    fun clear() {
        itemsMapState.clear()
        isInitialized = false
        actionMode = null
        blockItems = false
    }

    fun clearViews() {
        actionMode = null
        actionModeCallback = null
        view = null
    }

    fun setView(
        actionModeInstance: ActionMode.Callback? = null
    ) {
        actionModeCallback = actionModeInstance
    }

    fun getItemState(pos: Long) = itemsMapState.getOrElse(pos) { false }

    fun setItemState(pos: Long, state: Boolean) {
        if (!state) itemsMapState.remove(pos) else itemsMapState[pos] = true
        if (itemsMapState.size != 0) actionMode?.invalidate()
        else closeActionMode()
    }

}