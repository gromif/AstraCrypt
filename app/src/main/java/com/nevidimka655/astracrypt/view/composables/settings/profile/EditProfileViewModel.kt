package com.nevidimka655.astracrypt.view.composables.settings.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.core.di.IoDispatcher
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.domain.model.profile.Avatars
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val keysetManager: KeysetManager,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    //val imageLoader: ImageLoader,
    filesUtil: FilesUtil
): ViewModel() {
    private val iconFile = filesUtil.getProfileIconFile()
    val profileInfoFlow get() = settingsDataStoreManager.profileInfoFlow
    //val coilAvatarModel = CoilTinkModel(absolutePath = iconFile.toString())
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

    /*fun setGalleryAvatar(context: Context, uri: Uri) = viewModelScope.launch(defaultDispatcher) {
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

        iconFile.createNewFile()
        val outputStream = aeadInfo.preview?.let {
            keysetManager.stream(it)
                .streamingAead()
                .newEncryptingStream(iconFile.outputStream(), keysetManager.associatedData)
        } ?: iconFile.outputStream()

        outputStream.use { it.write(compressedByteStream.toByteArray()) }

        setDefaultAvatar(avatar = null)
        isImageProcessing = false
    }*/

}