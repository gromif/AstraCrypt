package io.gromif.astracrypt.auth.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.DecryptTinkAdUseCase
import javax.inject.Inject

@HiltViewModel
internal class PasswordLoginViewModel @Inject constructor(
    private val verifyAuthUseCase: VerifyAuthUseCase,
    private val decryptTinkAdUseCase: DecryptTinkAdUseCase,
    getAuthFlowUseCase: GetAuthFlowUseCase
): ViewModel() {
    val authState = getAuthFlowUseCase()

    suspend fun verifyPassword(password: String): Boolean {
        return verifyAuthUseCase(password = password)
    }

    suspend fun decryptTinkAd(password: String) {
        decryptTinkAdUseCase(password)
    }

}