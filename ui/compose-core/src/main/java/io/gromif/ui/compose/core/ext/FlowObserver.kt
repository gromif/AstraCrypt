@file:Suppress("NOTHING_TO_INLINE")

package io.gromif.ui.compose.core.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> FlowObserver(
    flow: Flow<T>,
    action: suspend (T) -> Unit
) = LaunchedEffect(Unit) {
    flow.collectLatest(action = action)
}