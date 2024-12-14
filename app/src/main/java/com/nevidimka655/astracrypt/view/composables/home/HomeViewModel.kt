package com.nevidimka655.astracrypt.view.composables.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    filesRepositoryProvider: FilesRepositoryProvider,
    io: Io,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    val imageLoader: ImageLoader
) : ViewModel() {
    val coilAvatarModel = CoilTinkModel(absolutePath = io.getProfileIconFile().toString())

    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow
    val recentFilesStateFlow = filesRepositoryProvider.repositoryFlow { it.getRecentFilesFlow() }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}