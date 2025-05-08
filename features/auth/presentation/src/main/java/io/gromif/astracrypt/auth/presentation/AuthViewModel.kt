package io.gromif.astracrypt.auth.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    getAuthStateFlowUseCase: GetAuthStateFlowUseCase
): ViewModel() {
    val authStateFlow = getAuthStateFlowUseCase()

}