package io.gromif.astracrypt.presentation.navigation.shared

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterExitState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun AnimatedContentScope.UiStateHandler(
    vararg keys: Any,
    block: suspend CoroutineScope.() -> Unit
) {
    val currentState = transition.currentState
    val targetState = transition.targetState

    LaunchedEffect(currentState, targetState, *keys) {
        val isCurrentStateVisible = currentState == EnterExitState.Visible
        val isTargetStateVisible = targetState == EnterExitState.Visible
        if (isTargetStateVisible || isCurrentStateVisible) {
            block()
        }
    }

}