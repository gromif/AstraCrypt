package com.nevidimka655.astracrypt.view.composables.settings.security.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.AeadManager
import com.nevidimka655.astracrypt.app.utils.AppComponentManager
import com.nevidimka655.astracrypt.features.auth.AuthManager
import com.nevidimka655.astracrypt.features.auth.model.Skin
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.sha256
import com.nevidimka655.crypto.tink.extensions.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsAuthViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val authManager: AuthManager,
    private val aeadManager: AeadManager,
    private val keysetFactory: KeysetFactory,
    private val appComponentManager: AppComponentManager
) : ViewModel() {
    val authInfoFlow get() = authManager.infoFlow
    val aeadInfoFlow get() = aeadManager.infoFlow

    fun disable() = viewModelScope.launch(defaultDispatcher) {
        authManager.disable()
    }

    fun disableSkin() = viewModelScope.launch(defaultDispatcher) {
        authManager.setSkin(skin = null)
        with(appComponentManager) {
            main = true
            calculator = false
        }
    }

    fun setCalculatorSkin(combination: String) = viewModelScope.launch(defaultDispatcher) {
        val skin = Skin.Calculator().apply {
            combinationHash = combination.sha256().toBase64()
        }
        authManager.setSkin(skin = skin)
        with(appComponentManager) {
            calculator = true
            main = false
        }
    }

    fun setBindAssociatedData(
        state: Boolean, password: String
    ) = viewModelScope.launch(defaultDispatcher) {
        launch { aeadManager.setBindAssociatedData(state = state) }
        launch {
            if (state) keysetFactory.encryptAssociatedData(password)
            else keysetFactory.decryptAssociatedData()
        }
    }

    fun setPassword(password: String) = viewModelScope.launch(defaultDispatcher) {
        authManager.setPassword(password = password)
    }

    suspend fun verifyPassword(password: String) = withContext(defaultDispatcher) {
        authManager.verifyPassword(password = password)
    }

    fun setHintState(state: Boolean) = viewModelScope.launch(defaultDispatcher) {
        authManager.setHintState(state = state)
    }

    fun setHintText(text: String) = viewModelScope.launch(defaultDispatcher) {
        authManager.setHintText(text = text)
    }

}