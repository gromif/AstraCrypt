package com.nevidimka655.astracrypt.ui.tabs.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    val imageLoader: ImageLoader
): ViewModel() {
    var optionsItem by mutableStateOf(StorageItemListTuple())

    val sheetOptionsState = mutableStateOf(false)
    val dialogRenameState = mutableStateOf(false)
    val dialogDeleteState = mutableStateOf(false)

    fun setStarredFlag(
        state: Boolean,
        id: Long? = null,
        itemsArr: List<Long>? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        Repository.setStarred(id, itemsArr, state)
        /*showSnackbar(
            if (state) R.string.snack_starred else R.string.snack_unstarred
        )*/ // TODO: Snackbar
    }

}