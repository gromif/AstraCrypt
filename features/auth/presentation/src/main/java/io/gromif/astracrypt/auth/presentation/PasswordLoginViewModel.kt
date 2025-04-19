package io.gromif.astracrypt.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.DecryptTinkAdUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PasswordLoginViewModel @Inject constructor(
    private val verifyAuthUseCase: VerifyAuthUseCase,
    private val decryptTinkAdUseCase: DecryptTinkAdUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase
): ViewModel() {
    private val authState = getAuthFlowUseCase().stateIn(
        viewModelScope, SharingStarted.Eagerly, initialValue = Auth()
    )

    suspend fun verifyPassword(password: String): Boolean {
        return verifyAuthUseCase(password = password)
    }

    fun isTinkAdTiedToAuth(): Boolean = authState.value.bindTinkAd

    suspend fun decryptTinkAd(password: String) {
        decryptTinkAdUseCase(password)
    }

}