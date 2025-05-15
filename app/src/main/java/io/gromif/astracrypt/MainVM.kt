package io.gromif.astracrypt

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import contract.auth.AuthContract
import contract.secure_content.SecureContentContract
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.utils.AppearanceManager
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_QUERY = "search_query"

@HiltViewModel
class MainVM @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val authContract: AuthContract,
    secureContentContract: SecureContentContract,
    val appearanceManager: AppearanceManager
) : ViewModel(), DefaultLifecycleObserver {
    var uiState = mutableStateOf(UiState())
    val searchQueryState = state.getStateFlow(SEARCH_QUERY, "")

    val secureContentStateFlow = secureContentContract.getContractModeFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        SecureContentContract.Mode.ENABLED
    )
    val authStateFlow = authContract.getAuthStateFlow()

    fun setSearchQuery(query: String) {
        state[SEARCH_QUERY] = query
    }

    fun setDynamicColorsState(enabled: Boolean) = viewModelScope.launch(defaultDispatcher) {
        appearanceManager.setDynamicTheme(enabled = enabled)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        viewModelScope.launch(defaultDispatcher) { authContract.updateLastActiveTime() }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        viewModelScope.launch(defaultDispatcher) { authContract.verifyTimeout() }
    }
}
