package com.nevidimka655.astracrypt.view.composables.home

import androidx.lifecycle.ViewModel
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    filesUtil: FilesUtil,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    //val imageLoader: ImageLoader
) : ViewModel() {
    //val coilAvatarModel = CoilTinkModel(absolutePath = filesUtil.getProfileIconFile().toString())

    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow
    /*val recentFilesStateFlow = repositoryProviderImpl.repositoryFlow {
        it.getRecentFilesFlow().map { list ->
            list.map {
                FileItem(
                    id = it.id,
                    name = it.name,
                    type = FileTypes.entries[it.itemType]
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())*/

}