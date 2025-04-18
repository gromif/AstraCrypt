package io.gromif.secure_content.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.usecase.GetContentModeFlowUseCase
import io.gromif.secure_content.domain.usecase.SetContentModeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SecureContentSettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val setContentModeUseCase: SetContentModeUseCase,
    getContentModeFlowUseCase: GetContentModeFlowUseCase
): ViewModel() {
    val contentModeFlow = getContentModeFlowUseCase()

    fun setMode(mode: SecureContentMode) = viewModelScope.launch(defaultDispatcher) {
        setContentModeUseCase(mode)
    }

}