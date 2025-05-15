package io.gromif.astracrypt.auth.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetBindTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.hint.SetHintTextUseCase
import io.gromif.astracrypt.auth.domain.usecase.hint.SetHintVisibilityUseCase
import io.gromif.astracrypt.auth.domain.usecase.skin.SetSkinTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.SetTimeoutUseCase
import io.gromif.astracrypt.utils.app.AppComponentService
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("detekt:LongParameterList")
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
    private val setTimeoutUseCase: SetTimeoutUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase
) : ViewModel() {
    val authFlow = getAuthFlowUseCase()

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
        state: Boolean,
        password: String
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

    fun setTimeout(timeout: Timeout) = viewModelScope.launch(defaultDispatcher) {
        setTimeoutUseCase(timeout)
    }
}
