package io.gromif.ui.compose.core.ext

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWindowWidth = staticCompositionLocalOf { WindowWidthSizeClass.Compact }
val LocalWindowHeight = staticCompositionLocalOf { WindowHeightSizeClass.Compact }

val WindowWidthSizeClass.isCompact get() = this == WindowWidthSizeClass.Compact
val WindowWidthSizeClass.isMedium get() = this == WindowWidthSizeClass.Medium
val WindowWidthSizeClass.isExpanded get() = this == WindowWidthSizeClass.Expanded

val WindowHeightSizeClass.isCompact get() = this == WindowHeightSizeClass.Compact
val WindowHeightSizeClass.isMedium get() = this == WindowHeightSizeClass.Medium
val WindowHeightSizeClass.isExpanded get() = this == WindowHeightSizeClass.Expanded

fun WindowWidthSizeClass.cellsCount(compact: Int = 1, medium: Int = 2, expanded: Int = 3) =
    when (this) {
        WindowWidthSizeClass.Compact -> compact
        WindowWidthSizeClass.Medium -> medium
        else -> expanded
    }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun windowSizeClassLocalProviders(activity: Activity): Array<ProvidedValue<*>> {
    val windowSize = calculateWindowSizeClass(activity = activity)
    return arrayOf(
        LocalWindowHeight provides windowSize.heightSizeClass,
        LocalWindowWidth provides windowSize.widthSizeClass,
    )
}