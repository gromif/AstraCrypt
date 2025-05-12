package io.gromif.ui.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

object Compose {

    @Suppress("NOTHING_TO_INLINE")
    @Composable
    inline fun state(defaultState: Boolean = false) =
        rememberSaveable { mutableStateOf(defaultState) }

}