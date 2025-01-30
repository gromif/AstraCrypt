package com.nevidimka655.astracrypt.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.usecase.DecryptTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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