package io.gromif.astracrypt.profile.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.profile.di.AvatarImageLoader
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.usecase.GetProfileFlowUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetAvatarUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetExternalAvatarUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetNameUseCase
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
    private val setNameUsecase: SetNameUseCase,
    private val setAvatarUsecase: SetAvatarUseCase,
    private val setExternalAvatarUsecase: SetExternalAvatarUseCase,
    @AvatarImageLoader
    val imageLoader: ImageLoader,
    getValidationRulesUsecase: GetValidationRulesUseCase,
    getProfileFlowUsecase: GetProfileFlowUseCase,
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