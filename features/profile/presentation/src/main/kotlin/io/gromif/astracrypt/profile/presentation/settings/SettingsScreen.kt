package io.gromif.astracrypt.profile.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.ui.compose_core.Compose
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.presentation.settings.contracts.Contracts
import io.gromif.astracrypt.profile.presentation.settings.contracts.externalAvatar
import io.gromif.astracrypt.profile.presentation.shared.Profile

@Composable
fun Profile.SettingsScreen() {
    val vm: SettingsViewModel = hiltViewModel()
    val profileInfo by vm.profileState.collectAsStateWithLifecycle()
    var hideAvatarState by Compose.state()

    val externalAvatarContract = Contracts.externalAvatar {
        hideAvatarState = true
        vm.setExternalAvatar(path = it.toString()).invokeOnCompletion {
            hideAvatarState = false
        }
    }

    Screen(
        maxNameLength = vm.validationRules.maxNameLength,
        profile = profileInfo,
        imageLoader = vm.imageLoader,
        hideIcon = hideAvatarState,
        actions = object : Actions {
            override fun changeName(name: String) {
                vm.setName(name)
            }

            override fun setDefaultAvatar(defaultAvatar: DefaultAvatar) {
                vm.setAvatar(defaultAvatar)
            }

            override fun setExternalAvatar() = externalAvatarContract.launch("image/*")
        }
    )
}