package com.nevidimka655.astracrypt.ui.tabs.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nevidimka655.astracrypt.room.StorageItemListTuple

class FilesViewModel: ViewModel() {
    var optionsItem by mutableStateOf(StorageItemListTuple())

    val sheetOptionsState = mutableStateOf(false)
    val dialogRenameState = mutableStateOf(false)

}