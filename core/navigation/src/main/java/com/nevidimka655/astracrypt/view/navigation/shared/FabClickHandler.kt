package com.nevidimka655.astracrypt.view.navigation.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun FabClickHandler(
    onFabClick: Flow<Any>,
    action: suspend (Any) -> Unit
) = LaunchedEffect(Unit) {
    onFabClick.collectLatest(action = action)
}