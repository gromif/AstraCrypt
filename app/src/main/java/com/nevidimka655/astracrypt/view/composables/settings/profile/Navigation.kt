package com.nevidimka655.astracrypt.view.composables.settings.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val EditProfileUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_editProfile)
    )
)


fun NavGraphBuilder.editProfile(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.EditProfile> {
    onUiStateChange(EditProfileUiState)
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