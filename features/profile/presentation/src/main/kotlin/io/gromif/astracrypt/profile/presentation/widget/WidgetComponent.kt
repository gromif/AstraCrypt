package io.gromif.astracrypt.profile.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.profile.presentation.shared.Profile

@Composable
fun Profile.WidgetComponent(modifier: Modifier = Modifier) {
    val vm: WidgetViewModel = hiltViewModel()
    val profileState by vm.profileState.collectAsStateWithLifecycle()

    Widget(
        modifier = modifier,
        imageLoader = vm.imageLoader,
        profile = profileState
    )

}