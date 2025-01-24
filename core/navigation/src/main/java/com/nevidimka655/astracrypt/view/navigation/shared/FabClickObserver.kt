package com.nevidimka655.astracrypt.view.navigation.shared

import androidx.compose.runtime.Composable
import com.nevidimka655.ui.compose_core.ext.FlowObserver
import kotlinx.coroutines.flow.Flow

@Composable
internal fun FabClickObserver(
    onFabClick: Flow<Any>,
    action: suspend (Any) -> Unit
) = FlowObserver(onFabClick, action)