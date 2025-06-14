package io.gromif.calculator.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
internal fun Buttons.Button(
    symbol: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textStyle: TextStyle = TextStyle(),
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        Text(
            text = symbol,
            style = textStyle,
            fontSize = 26.sp,
            color = textColor
        )
    }
}

@Composable
internal fun Buttons.ButtonTertiary(
    symbol: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    onClick: () -> Unit
) = Button(
    symbol = symbol,
    modifier = modifier,
    backgroundColor = MaterialTheme.colorScheme.tertiary,
    textColor = MaterialTheme.colorScheme.onTertiary,
    textStyle = textStyle,
    onClick = onClick
)

@Composable
internal fun Buttons.ButtonSecondary(
    symbol: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    onClick: () -> Unit
) = Button(
    symbol = symbol,
    modifier = modifier,
    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
    textColor = MaterialTheme.colorScheme.onSecondaryContainer,
    textStyle = textStyle,
    onClick = onClick
)