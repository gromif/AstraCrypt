package com.nevidimka655.astracrypt.tabs.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.profile.AvatarIds
import com.nevidimka655.astracrypt.features.profile.ui.ProfileIcon
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.CenterCropTransformation
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.extensions.ui.viewLifecycleScope
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.dialogs.Dialog
import com.nevidimka655.ui.compose_core.dialogs.DialogDefaults
import com.nevidimka655.ui.compose_core.dialogs.Dialogs
import com.nevidimka655.ui.compose_core.dialogs.default
import com.nevidimka655.ui.compose_core.ext.vectorResource
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()
    private val imageLoader get() = Engine.imageLoader

    @SuppressLint("NewApi")
    val photoContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) viewLifecycleScope.launch(Dispatchers.IO) {
            val drawable =
                if (Api.atLeastAndroid10()) requireContext().contentResolver.loadThumbnail(
                    it, Size(AppConfig.DB_THUMB_SIZE, AppConfig.DB_THUMB_SIZE), null
                ).toDrawable(resources) else {
                    val request = ImageRequest.Builder(requireContext())
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .size(AppConfig.DB_THUMB_SIZE)
                        .scale(Scale.FILL)
                        .transformations(CenterCropTransformation())
                        .data(it)
                        .build()
                    imageLoader.execute(request).drawable
                }
            vm.saveProfileInfo(
                profileInfo = vm.profileInfoFlow.value.copy(
                    defaultAvatar = null,
                    iconFile = drawable
                )
            )
        }
    }

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                EditProfileScreen()
            }
        }
    }

    @SuppressLint("NewApi")
    @Composable
    fun EditProfileScreen() {
        PreferencesScreen {
            val profileInfo by vm.profileInfoFlow.collectAsStateWithLifecycle()
            val name = remember(profileInfo) { profileInfo.name ?: getString(R.string.user) }
            val dialogChangeNameState = dialogChangeName(currentName = name) {
                vm.saveProfileInfo(profileInfo.copy(name = it.trim()))
            }
            val dialogChangeAvatarState = Compose.state()
            PreferencesGroup {
                Preference(
                    titleText = getString(R.string.settings_changeName),
                    summaryText = name
                ) { dialogChangeNameState.value = true }
                Preference(
                    titleText = getString(R.string.settings_changeAvatar),
                    trailingContent = {
                        ProfileIcon(profileInfo = profileInfo, iconSize = 56.dp, showBorder = false)
                    }
                ) { dialogChangeAvatarState.value = true }
            }
            DialogChangeAvatar(
                state = dialogChangeAvatarState,
                galleryCallback = {
                    photoContract.launch("image/*")
                },
                onDefaultAvatarClick = {
                    vm.saveProfileInfo(
                        profileInfo = profileInfo.copy(
                            defaultAvatar = it.ordinal,
                            iconFile = null
                        ),
                        force = true
                    )
                }
            )
        }
    }

    @Composable
    private fun dialogChangeName(
        currentName: String,
        onNameChange: (String) -> Unit
    ) = Dialogs.TextFields.default(
        title = stringResource(id = R.string.settings_changeName),
        params = Dialogs.TextFields.Params(
            text = currentName,
            label = stringResource(id = R.string.name),
            singleLine = true,
            maxLength = AppConfig.PROFILE_NAME_MAX_SIZE,
        ),
        onResult = onNameChange
    )

    @Composable
    private fun DialogChangeAvatar(
        state: MutableState<Boolean>,
        galleryCallback: () -> Unit,
        onDefaultAvatarClick: (AvatarIds) -> Unit
    ) {
        if (!state.value) return
        Dialog(
            title = DialogDefaults.titleCentered(title = getString(R.string.settings_changeAvatar)),
            onDismissRequest = { state.value = false },
            confirmButton = DialogDefaults.textButton(title = stringResource(id = android.R.string.cancel)) {
                state.value = false
            },
            dismissButton = DialogDefaults.textButton(title = stringResource(id = R.string.gallery)) {
                state.value = false
                galleryCallback()
            },
            content = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
                ) {
                    items(AvatarIds.entries) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        state.value = false
                                        onDefaultAvatarClick(it)
                                    },
                                imageVector = vectorResource(id = it.resId),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        )
    }

}