package io.gromif.astracrypt.presentation.navigation.shared

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import io.gromif.astracrypt.presentation.navigation.models.HostEvents
import io.gromif.astracrypt.presentation.navigation.models.HostStateHolder

internal val LocalNavController =
    compositionLocalOf<NavController> { error("No valid NavController found") }

internal val LocalHostStateHolder =
    compositionLocalOf<HostStateHolder> { error("No valid HostStateHolder found") }

internal val LocalHostEvents =
    compositionLocalOf<HostEvents> { error("No valid HostEvents found") }