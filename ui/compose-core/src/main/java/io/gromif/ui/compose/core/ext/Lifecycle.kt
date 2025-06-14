package io.gromif.ui.compose.core.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

@Composable
fun <ViewModel : LifecycleObserver> ViewModel.ObserveLifecycleEvents(
    lifecycle: Lifecycle
) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@ObserveLifecycleEvents)

        onDispose {
            lifecycle.removeObserver(this@ObserveLifecycleEvents)
        }
    }
}