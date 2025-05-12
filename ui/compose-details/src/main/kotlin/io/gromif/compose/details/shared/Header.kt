package io.gromif.compose.details.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.gromif.ui.compose.core.ext.LocalWindowHeight
import io.gromif.ui.compose.core.ext.isCompact
import io.gromif.ui.compose.core.ext.isMedium
import io.gromif.ui.compose.core.theme.spaces

@Composable
internal fun Header(
    headerImage: @Composable BoxScope.() -> Unit = {},
    title: String = "TEST_TITLE"
) {
    val height = LocalWindowHeight.current
    val adaptiveImageWidth = remember {
        when {
            height.isCompact -> 180.dp
            height.isMedium -> 280.dp
            else -> 320.dp
        }
    }

    @Composable
    fun content() {
        Surface(modifier = Modifier.width(adaptiveImageWidth), shape = MaterialTheme.shapes.large) {
            Box(modifier = Modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
                headerImage()
            }
        }
    }

    if (height.isCompact) Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(MaterialTheme.spaces.spaceMedium),
    ) {
        content()
        HeaderTitle(title = title, padding = 10.dp)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(MaterialTheme.spaces.spaceMedium),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
        HeaderTitle(title = title)
    }
}

@Composable
private fun HeaderTitle(title: String, padding: Dp = 30.dp) = Text(
    modifier = Modifier.padding(padding),
    text = title,
    style = MaterialTheme.typography.titleMedium
)