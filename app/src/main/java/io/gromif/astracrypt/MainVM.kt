package io.gromif.astracrypt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.VerifySkinUseCase
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.utils.AppearanceManager
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.usecase.GetContentModeFlowUseCase
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
    private val verifySkinUseCase: VerifySkinUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase,
    getContentModeFlowUseCase: GetContentModeFlowUseCase,
    val appearanceManager: AppearanceManager
) : ViewModel() {
    var uiState = mutableStateOf(UiState())
    val searchQueryState = state.getStateFlow(SEARCH_QUERY, "")

    val secureContentStateFlow = getContentModeFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SecureContentMode.ENABLED)

    var userIsAuthenticated by mutableStateOf(false)
    var skinIsAuthenticated by mutableStateOf(false)
    val authState = getAuthFlowUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(),
        initialValue = null
    )

    fun verifySkin(data: String) = viewModelScope.launch(defaultDispatcher) {
        skinIsAuthenticated = verifySkinUseCase(data)
    }

    fun setSearchQuery(query: String) {
        state[SEARCH_QUERY] = query
    }

    fun setDynamicColorsState(enabled: Boolean) = viewModelScope.launch(defaultDispatcher) {
        appearanceManager.setDynamicTheme(enabled = enabled)
    }

}