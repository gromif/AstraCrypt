package com.nevidimka655.astracrypt.view.navigation.shared

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterExitState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun AnimatedContentScope.UiStateWhenVisible(
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