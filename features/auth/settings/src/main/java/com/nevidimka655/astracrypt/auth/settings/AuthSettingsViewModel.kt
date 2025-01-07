package com.nevidimka655.astracrypt.auth.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.SkinType
import com.nevidimka655.astracrypt.auth.domain.usecase.DisableAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintTextUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetPasswordUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinDefaultUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import com.nevidimka655.astracrypt.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class AuthSettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val disableAuthUseCase: DisableAuthUseCase,
    private val setSkinDefaultUseCase: SetSkinDefaultUseCase,
    private val setSkinUseCase: SetSkinUseCase,
    private val setPasswordUseCase: SetPasswordUseCase,
    private val verifyAuthUseCase: VerifyAuthUseCase,
    private val setHintVisibilityUseCase: SetHintVisibilityUseCase,
    private val setHintTextUseCase: SetHintTextUseCase,
    private val setBindTinkAdUseCase: SetBindTinkAdUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase
) : ViewModel() {
    val authState = getAuthFlowUseCase().stateIn(viewModelScope, SharingStarted.Lazily, Auth())

    fun disable() = viewModelScope.launch(defaultDispatcher) {
        disableAuthUseCase(authState.value)
    }

    fun disableSkin() = viewModelScope.launch(defaultDispatcher) {
        setSkinDefaultUseCase(auth = authState.value)
        /*with(appComponentService) {
            main = true
            calculator = false
        }*/
    }

    fun setCalculatorSkin(combination: String) = viewModelScope.launch(defaultDispatcher) {
        setSkinUseCase(
            auth = authState.value,
            skinType = SkinType.Calculator,
            data = combination
        )
        /*with(appComponentService) {
            calculator = true
            main = false
        }*/
    }

    fun setBindAssociatedData(
        state: Boolean, password: String
    ) = viewModelScope.launch(defaultDispatcher) {
        setBindTinkAdUseCase(
            auth = authState.value,
            bind = state,
            password = password
        )
    }

    fun setPassword(password: String) = viewModelScope.launch(defaultDispatcher) {
        setPasswordUseCase(auth = authState.value, data = password)
    }

    suspend fun verifyPassword(password: String): Boolean = withContext(defaultDispatcher) {
        verifyAuthUseCase(password = password)
    }

    fun setHintState(state: Boolean) = viewModelScope.launch(defaultDispatcher) {
        setHintVisibilityUseCase(auth = authState.value, visible = state)
    }

    fun setHintText(text: String) = viewModelScope.launch(defaultDispatcher) {
        setHintTextUseCase(auth = authState.value, text = text)
    }

}