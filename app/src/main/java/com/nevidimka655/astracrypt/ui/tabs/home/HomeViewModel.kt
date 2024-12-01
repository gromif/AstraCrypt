package com.nevidimka655.astracrypt.ui.tabs.home

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.nevidimka655.astracrypt.model.CoilTinkModel
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val settingsDataStoreManager: SettingsDataStoreManager,
    val imageLoader: ImageLoader,
    io: Io,
    encryptionManager: EncryptionManager,
): ViewModel() {

    val coilAvatarModel = CoilTinkModel(
        absolutePath = io.getProfileIconFile().toString(),
        encryptionType = encryptionManager.encryptionInfo.thumbEncryptionOrdinal
    )

    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow

}