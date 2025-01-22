package com.nevidimka655.astracrypt.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.app.utils.FileSystemSetupManager
import com.nevidimka655.astracrypt.auth.domain.usecase.GetAuthFlowUseCase
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifySkinUseCase
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val fileSystemSetupManager: FileSystemSetupManager,
    private val verifySkinUseCase: VerifySkinUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase,
    val appearanceManager: AppearanceManager
) : ViewModel() {
    var uiState = mutableStateOf(UiState())
    val searchQueryState = state.getStateFlow(SEARCH_QUERY, "")

    var userIsAuthenticated by mutableStateOf(false)
    var skinIsAuthenticated by mutableStateOf(false)
    val authState = getAuthFlowUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    fun verifySkin(data: String) = viewModelScope.launch {
        skinIsAuthenticated = verifySkinUseCase(data)
    }

    fun setSearchQuery(query: String) {
        state[SEARCH_QUERY] = query
    }

    init {
        if (!fileSystemSetupManager.isDatabaseCreated())
            viewModelScope.launch(defaultDispatcher) { fileSystemSetupManager.setup() }
    }

}