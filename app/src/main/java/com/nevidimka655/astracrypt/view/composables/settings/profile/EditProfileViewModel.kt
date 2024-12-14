package com.nevidimka655.astracrypt.view.composables.settings.profile

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
import com.nevidimka655.astracrypt.app.config.AppConfig
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.extensions.recreate
import com.nevidimka655.astracrypt.app.utils.AeadManager
import com.nevidimka655.astracrypt.app.utils.Api
import com.nevidimka655.astracrypt.app.utils.CenterCropTransformation
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.features.profile.model.Avatars
import com.nevidimka655.astracrypt.features.profile.model.ProfileInfo
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val keysetFactory: KeysetFactory,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    private val aeadManager: AeadManager,
    val imageLoader: ImageLoader,
    io: Io
): ViewModel() {
    private val iconFile = io.getProfileIconFile()
    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow
    val coilAvatarModel = CoilTinkModel(absolutePath = iconFile.toString())
    var isImageProcessing by mutableStateOf(false)

    private suspend fun updateProfile(
        updateBlock: (ProfileInfo) -> ProfileInfo
    ) = withContext(defaultDispatcher) {
        val newProfile = updateBlock(profileInfoFlow.first())
        settingsDataStoreManager.setProfileInfo(newProfile)
    }

    fun updateName(name: String) = viewModelScope.launch(defaultDispatcher) {
        updateProfile { it.copy(name = name) }
    }

    fun setDefaultAvatar(avatar: Avatars?) = viewModelScope.launch(defaultDispatcher) {
        updateProfile { it.copy(defaultAvatar = avatar) }
    }

    fun setGalleryAvatar(context: Context, uri: Uri) = viewModelScope.launch(defaultDispatcher) {
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
        val aeadInfo = aeadManager.getInfo()

        iconFile.recreate()
        val outputStream = aeadInfo.preview?.let {
            keysetFactory.stream(it)
                .streamingAeadPrimitive()
                .newEncryptingStream(iconFile.outputStream(), keysetFactory.associatedData)
        } ?: iconFile.outputStream()

        outputStream.use { it.write(compressedByteStream.toByteArray()) }

        setDefaultAvatar(avatar = null)
        isImageProcessing = false
    }

}