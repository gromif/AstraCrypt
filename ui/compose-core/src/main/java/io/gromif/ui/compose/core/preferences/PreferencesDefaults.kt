package io.gromif.ui.compose.core.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.gromif.ui.compose.core.ext.LocalWindowWidth
import io.gromif.ui.compose.core.theme.spaces

object PreferencesDefaults {
    object Screen {
        val contentPadding
            @Composable get() = PaddingValues(MaterialTheme.spaces.spaceMedium)

        val verticalArrangement: Arrangement.Vertical
            @Composable get() = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)

        val horizontalArrangement: Arrangement.Horizontal
            @Composable get() = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)

        val columns: Int
            @Composable get() {
                val localWidth = LocalWindowWidth.current
                return remember(localWidth) {
                    when (localWidth) {
                        WindowWidthSizeClass.Expanded -> 3
                        WindowWidthSizeClass.Medium -> 2
                        else -> 1
                    }
                }
            }
    }
}
