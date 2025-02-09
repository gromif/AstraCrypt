package com.nevidimka655.astracrypt.auth.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetAuthTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetBindTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetHintTextUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetHintVisibilityUseCase
import io.gromif.astracrypt.auth.domain.usecase.SetSkinTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.VerifyAuthUseCase
import io.gromif.astracrypt.utils.app.AppComponentService
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
    private val setSkinTypeUseCase: SetSkinTypeUseCase,
    private val setAuthTypeUseCase: SetAuthTypeUseCase,
    private val verifyAuthUseCase: VerifyAuthUseCase,
    private val setHintVisibilityUseCase: SetHintVisibilityUseCase,
    private val setHintTextUseCase: SetHintTextUseCase,
    private val setBindTinkAdUseCase: SetBindTinkAdUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase
) : ViewModel() {
    val authState = getAuthFlowUseCase().stateIn(viewModelScope, SharingStarted.Lazily, Auth())

    fun disable() = viewModelScope.launch(defaultDispatcher) {
        setAuthTypeUseCase(authType = null, data = null)
    }

    fun disableSkin() = viewModelScope.launch(defaultDispatcher) {
        setSkinTypeUseCase(skinType = null, data = null)
        with(appComponentService) {
            main = true
            calculator = false
        }
    }

    fun setCalculatorSkin(combination: String) = viewModelScope.launch(defaultDispatcher) {
        setSkinTypeUseCase(skinType = SkinType.Calculator, data = combination)
        with(appComponentService) {
            calculator = true
            main = false
        }
    }

    fun setBindAssociatedData(
        state: Boolean, password: String
    ) = viewModelScope.launch(defaultDispatcher) {
        setBindTinkAdUseCase(bind = state, password = password)
    }

    fun setPassword(password: String) = viewModelScope.launch(defaultDispatcher) {
        setAuthTypeUseCase(authType = AuthType.PASSWORD, data = password)
    }

    suspend fun verifyPassword(password: String): Boolean = withContext(defaultDispatcher) {
        verifyAuthUseCase(password = password)
    }

    fun setHintState(state: Boolean) = viewModelScope.launch(defaultDispatcher) {
        setHintVisibilityUseCase(visible = state)
    }

    fun setHintText(text: String) = viewModelScope.launch(defaultDispatcher) {
        setHintTextUseCase(text = text)
    }

}