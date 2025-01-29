package io.gromif.astracrypt.profile.presentation.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.profile.di.AvatarImageLoader
import io.gromif.astracrypt.profile.domain.usecase.GetProfileUsecase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class WidgetViewModel @Inject constructor(
    @AvatarImageLoader
    val imageLoader: ImageLoader,
    getProfileUsecase: GetProfileUsecase
): ViewModel() {
    val profileState = getProfileUsecase().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

}