package io.gromif.astracrypt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import contract.secure_content.SecureContentContract
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.usecase.SetLastActiveTimeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.CheckAuthTimeoutUseCase
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
    private val setLastActiveTimeUseCase: SetLastActiveTimeUseCase,
    private val checkAuthTimeoutUseCase: CheckAuthTimeoutUseCase,
    secureContentContract: SecureContentContract,
    getAuthFlowUseCase: GetAuthFlowUseCase,
    val appearanceManager: AppearanceManager
) : ViewModel(), DefaultLifecycleObserver {
    var uiState = mutableStateOf(UiState())
    val searchQueryState = state.getStateFlow(SEARCH_QUERY, "")

    val secureContentStateFlow = secureContentContract.getContractModeFlow().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), SecureContentContract.Mode.ENABLED
    )

    var userIsAuthenticated by mutableStateOf(false)
    var skinIsAuthenticated by mutableStateOf(false)
    val authFlow = getAuthFlowUseCase()

    fun setSearchQuery(query: String) {
        state[SEARCH_QUERY] = query
    }

    fun checkAuthTimeout() = viewModelScope.launch {
        val isAuthValid = checkAuthTimeoutUseCase()
        userIsAuthenticated = isAuthValid
        skinIsAuthenticated = isAuthValid
    }

    fun setDynamicColorsState(enabled: Boolean) = viewModelScope.launch(defaultDispatcher) {
        appearanceManager.setDynamicTheme(enabled = enabled)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        if (userIsAuthenticated) setLastActiveTimeUseCase()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        checkAuthTimeout()
    }
}