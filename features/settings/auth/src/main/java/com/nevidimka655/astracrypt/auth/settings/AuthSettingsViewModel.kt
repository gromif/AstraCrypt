package com.nevidimka655.astracrypt.auth.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.model.AuthType
import com.nevidimka655.astracrypt.auth.domain.model.SkinType
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetAuthUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintTextUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.SetSkinUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import com.nevidimka655.astracrypt.utils.app.AppComponentService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
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
    private val appComponentService: AppComponentService,
    private val setSkinUseCase: SetSkinUseCase,
    private val setAuthUseCase: SetAuthUseCase,
    private val verifyAuthUseCase: VerifyAuthUseCase,
    private val setHintVisibilityUseCase: SetHintVisibilityUseCase,
    private val setHintTextUseCase: SetHintTextUseCase,
    private val setBindTinkAdUseCase: SetBindTinkAdUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase
) : ViewModel() {
    val authState = getAuthFlowUseCase().stateIn(viewModelScope, SharingStarted.Lazily, Auth())

    fun disable() = viewModelScope.launch(defaultDispatcher) {
        setAuthUseCase(auth = authState.value, authType = null, data = null)
    }

    fun disableSkin() = viewModelScope.launch(defaultDispatcher) {
        setSkinUseCase(auth = authState.value, skinType = null, data = null)
        with(appComponentService) {
            main = true
            calculator = false
        }
    }

    fun setCalculatorSkin(combination: String) = viewModelScope.launch(defaultDispatcher) {
        setSkinUseCase(auth = authState.value, skinType = SkinType.Calculator, data = combination)
        with(appComponentService) {
            calculator = true
            main = false
        }
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
        setAuthUseCase(auth = authState.value, authType = AuthType.PASSWORD, data = password)
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