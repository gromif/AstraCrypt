package io.gromif.astracrypt.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.presentation.calculator.AuthCalculatorSkin
import io.gromif.astracrypt.auth.presentation.shared.onAuthType
import io.gromif.astracrypt.auth.presentation.shared.onSkinType
import kotlinx.coroutines.flow.Flow

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onFabClick: Flow<Any>,
    onAuthType: onAuthType,
    onSkinType: onSkinType
) {
    val vm: AuthViewModel = hiltViewModel()
    val authStateFlow by vm.authStateFlow.collectAsStateWithLifecycle(null)
    val authStateValue = authStateFlow ?: return

    val skinType = authStateValue.skinType
    val skinState = authStateValue.skinState

    if (skinType != null && !skinState) {
        when (skinType) {
            SkinType.Calculator -> AuthCalculatorSkin(modifier = modifier)
                .also { onSkinType.onCalculator() }
        }
        return
    }

    val authType = authStateValue.authType
    val authState = authStateValue.authState

    if (authType != null && !authState) {
        when (authType) {
            AuthType.PASSWORD -> PasswordLoginScreen(modifier = modifier, onFabClick = onFabClick)
                .also { onAuthType.onPassword() }
        }
    }
}
