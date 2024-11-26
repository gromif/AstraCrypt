package com.nevidimka655.astracrypt.ui.tabs.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    val imageLoader: ImageLoader
): ViewModel() {
    var optionsItem by mutableStateOf(StorageItemListTuple())

    val sheetOptionsState = mutableStateOf(false)
    val dialogRenameState = mutableStateOf(false)
    val dialogDeleteState = mutableStateOf(false)

}