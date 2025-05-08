package io.gromif.astracrypt.auth.presentation.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.usecase.skin.VerifySkinUseCase
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthCalculatorSkinViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val verifySkinUseCase: VerifySkinUseCase,
): ViewModel() {

    fun verifySkin(data: String) = viewModelScope.launch(defaultDispatcher) {
        verifySkinUseCase(data)
    }

}