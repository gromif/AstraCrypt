package com.nevidimka655.astracrypt.ui.navigation.composables.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.Route
import com.nevidimka655.astracrypt.ui.tabs.settings.profile.EditProfileScreen
import com.nevidimka655.astracrypt.ui.tabs.settings.profile.EditProfileViewModel

inline fun NavGraphBuilder.editProfile(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.EditProfile> {
    onUiStateChange(Route.EditProfile.Ui.state)
    val context = LocalContext.current
    val vm: EditProfileViewModel = hiltViewModel()
    val photoContract = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) vm.setGalleryAvatar(context = context, uri = it)
    }
    val profileInfo by vm.profileInfoFlow.collectAsStateWithLifecycle(initialValue = ProfileInfo())

    EditProfileScreen(
        imageLoader = vm.imageLoader,
        coilAvatarModel = vm.coilAvatarModel,
        defaultAvatar = profileInfo.defaultAvatar,
        name = profileInfo.name ?: stringResource(id = R.string.user),
        isImageProcessing = vm.isImageProcessing,
        onChangeName = { vm.updateName(name = it) },
        onDefaultAvatar = { vm.setDefaultAvatar(avatar = it) },
        onGalleryAvatar = { photoContract.launch("image/*") }
    )
}