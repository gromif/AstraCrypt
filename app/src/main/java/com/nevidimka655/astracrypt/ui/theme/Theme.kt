package com.nevidimka655.astracrypt.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.ui.theme.icons.Avatars
import com.nevidimka655.astracrypt.ui.theme.icons.Purchases
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.FileType
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.reset
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.resetAlt
import com.nevidimka655.astracrypt.ui.theme.icons.reset
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.compose_color_schemes._ColorSchemes
import com.nevidimka655.ui.compose_core.ext.windowSizeClassLocalProviders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

private var isDarkModeEnabled = false

@Composable
fun AstraCryptTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicThemeFlow: Flow<Boolean> = emptyFlow<Boolean>(),
    content: @Composable () -> Unit
) {
    _ColorSchemes.isDarkTheme = darkTheme
    val dynamicThemeState by dynamicThemeFlow.collectAsStateWithLifecycle(initialValue = true)
    val colorScheme = when {
        dynamicThemeState && Api.atLeast12() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> ColorSchemes.DarkColors
        else -> ColorSchemes.LightColors
    }
    if (isDarkModeEnabled != darkTheme) {
        isDarkModeEnabled = darkTheme
        Icons.reset()
    }
    val view = LocalView.current
    val activity = (view.context as Activity)
    if (!view.isInEditMode) SideEffect {
        val window = activity.window
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        CompositionLocalProvider(
            *windowSizeClassLocalProviders(activity),
            content = content
        )
    }
}

fun Icons.reset() = with(this) {
    Avatars.reset()
    Purchases.reset()
    FileType.reset()
    FileType.resetAlt()
}