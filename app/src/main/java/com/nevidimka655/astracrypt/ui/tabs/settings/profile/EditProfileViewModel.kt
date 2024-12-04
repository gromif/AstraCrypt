package com.nevidimka655.astracrypt.ui.tabs.settings.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.nevidimka655.astracrypt.features.profile.Avatars
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.model.CoilTinkModel
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.CenterCropTransformation
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.utils.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.utils.extensions.recreate
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val keysetFactory: KeysetFactory,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    private val encryptionManager: EncryptionManager,
    private val io: Io,
    val imageLoader: ImageLoader,
): ViewModel() {
    private val iconFile = io.getProfileIconFile()
    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow
    val coilAvatarModel = CoilTinkModel(absolutePath = io.getProfileIconFile().toString())
    var isImageProcessing by mutableStateOf(false)

    private suspend fun updateProfile(
        updateBlock: (ProfileInfo) -> ProfileInfo
    ) = withContext(Dispatchers.IO) {
        val newProfile = updateBlock(profileInfoFlow.first())
        settingsDataStoreManager.setProfileInfo(newProfile)
    }

    fun updateName(name: String) = viewModelScope.launch {
        updateProfile { it.copy(name = name) }
    }

    fun setDefaultAvatar(avatar: Avatars?) = viewModelScope.launch {
        updateProfile { it.copy(defaultAvatar = avatar) }
    }

    fun setGalleryAvatar(context: Context, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        isImageProcessing = true
        val bitmap = if (Api.atLeast10()) {
            context.contentResolver.loadThumbnail(
                uri, Size(AppConfig.DB_THUMB_SIZE, AppConfig.DB_THUMB_SIZE), null
            )
        } else imageLoader.execute(
            ImageRequest.Builder(context)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .size(AppConfig.DB_THUMB_SIZE)
                .scale(Scale.FILL)
                .transformations(CenterCropTransformation())
                .data(uri)
                .build()
        ).drawable!!.toBitmap()
        val compressedByteStream = ByteArrayOutputStream().also {
            bitmap.compress(Bitmap.CompressFormat.PNG, 98, it)
        }
        val encryptionInfo = encryptionManager.getInfo()
        val thumbEncryption = encryptionInfo.thumbEncryptionOrdinal

        iconFile.recreate()
        val outputStream = if (thumbEncryption != -1) {
            keysetFactory.stream(KeysetTemplates.Stream.entries[thumbEncryption])
                .streamingAeadPrimitive()
                .newEncryptingStream(iconFile.outputStream(), keysetFactory.associatedData)
        } else iconFile.outputStream()

        outputStream.use { it.write(compressedByteStream.toByteArray()) }

        setDefaultAvatar(avatar = null)
        isImageProcessing = false
    }

}