package io.gromif.astracrypt.settings.aead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.settings.aead.domain.usecase.GetSettingsAeadUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.SetSettingsAeadUseCase
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AeadViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val setSettingsAeadUseCase: SetSettingsAeadUseCase,
    getSettingsAeadUseCase: GetSettingsAeadUseCase,
): ViewModel() {

    val settingsAeadNameState = getSettingsAeadUseCase().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

    fun setSettingsAead(id: Int) = viewModelScope.launch(defaultDispatcher) {
        setSettingsAeadUseCase(aead = id)
    }

}