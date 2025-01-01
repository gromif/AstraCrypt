package com.nevidimka655.astracrypt.auth.login

import androidx.lifecycle.ViewModel
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.usecase.VerifyPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PasswordLoginViewModel @Inject constructor(
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    repository: Repository
): ViewModel() {
    private val authFlow = repository.getAuthFlow()

    suspend fun verifyPassword(password: String): Boolean {
        return verifyPasswordUseCase(password = password)
    }

}