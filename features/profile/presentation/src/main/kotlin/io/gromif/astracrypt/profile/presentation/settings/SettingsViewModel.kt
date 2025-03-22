package io.gromif.astracrypt.profile.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.profile.di.AvatarImageLoader
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.usecase.GetProfileFlowUsecase
import io.gromif.astracrypt.profile.domain.usecase.GetValidationRulesUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetAvatarUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetExternalAvatarUsecase
import io.gromif.astracrypt.profile.domain.usecase.SetNameUsecase
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val setNameUsecase: SetNameUsecase,
    private val setAvatarUsecase: SetAvatarUsecase,
    private val setExternalAvatarUsecase: SetExternalAvatarUsecase,
    @AvatarImageLoader
    val imageLoader: ImageLoader,
    getValidationRulesUsecase: GetValidationRulesUsecase,
    getProfileFlowUsecase: GetProfileFlowUsecase,
): ViewModel() {
    val validationRules = getValidationRulesUsecase()
    val profileState = getProfileFlowUsecase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), Profile()
    )

    fun setName(name: String) = viewModelScope.launch(defaultDispatcher) {
        setNameUsecase(name)
    }

    fun setAvatar(defaultAvatar: DefaultAvatar) = viewModelScope.launch(defaultDispatcher) {
        setAvatarUsecase(defaultAvatar)
    }

    fun setExternalAvatar(path: String) = viewModelScope.launch(defaultDispatcher) {
        setExternalAvatarUsecase(path)
    }

}