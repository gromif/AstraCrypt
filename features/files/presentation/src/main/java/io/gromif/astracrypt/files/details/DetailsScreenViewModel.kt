package io.gromif.astracrypt.files.details

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.nevidimka655.compose_details.mutableDetailsStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.details.parser.addAeadGroup
import io.gromif.astracrypt.files.details.parser.addDetailsGroup
import io.gromif.astracrypt.files.details.parser.addFlagsGroup
import io.gromif.astracrypt.files.details.parser.addFolderGroup
import io.gromif.astracrypt.files.di.FilesImageLoader
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.usecase.GetItemDetailsUseCase
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsScreenViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    @FilesImageLoader
    val imageLoader: ImageLoader
): ViewModel() {
    val detailsStateList = mutableDetailsStateList()
    var itemDetails by mutableStateOf<ItemDetails?>(null)

    fun loadItemDetails(context: Context, id: Long) = viewModelScope.launch(defaultDispatcher) {
        val details = getItemDetailsUseCase(id)
        val type = if (details is ItemDetails.File) details.type else ItemType.Folder

        detailsStateList.addDetailsGroup(type, details)
        when (details) {
            is ItemDetails.File -> with(detailsStateList) {
                addFlagsGroup(type = type, flags = details.flags)
                addAeadGroup(
                    fileAead = details.file.aeadIndex,
                    previewAead = details.preview?.aeadIndex
                )
            }
            is ItemDetails.Folder -> detailsStateList.addFolderGroup(
                context = context,
                filesCount = details.filesCount,
                foldersCount = details.foldersCount,
            )
        }
        itemDetails = details
    }

}