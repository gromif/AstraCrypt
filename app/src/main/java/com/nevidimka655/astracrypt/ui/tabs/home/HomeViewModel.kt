package com.nevidimka655.astracrypt.ui.tabs.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.nevidimka655.astracrypt.model.CoilTinkModel
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.RepositoryEncryption
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryEncryption: RepositoryEncryption,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    val imageLoader: ImageLoader,
    repository: Repository,
    io: Io,
    encryptionManager: EncryptionManager,
) : ViewModel() {

    val coilAvatarModel = CoilTinkModel(absolutePath = io.getProfileIconFile().toString())

    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow

    val recentFilesStateFlow = repository.getRecentFilesFlow().map { list ->
        if (encryptionManager.getInfo().db) try {
            list.map {
                repositoryEncryption.decryptStorageItemListTuple(it)
            }
        } catch (_: Exception) {
            list
        } else list
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

}