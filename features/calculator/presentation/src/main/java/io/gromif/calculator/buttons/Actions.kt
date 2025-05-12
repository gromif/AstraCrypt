package io.gromif.calculator.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Buttons.AC(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.ButtonTertiary(
    symbol = "AC",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.DEL(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.Button(
    symbol = "Del",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.DIVIDE(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.Button(
    symbol = "/",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.MULTIPLY(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.Button(
    symbol = "×",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.SUBTRACT(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.Button(
    symbol = "-",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.ADD(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.Button(
    symbol = "+",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.DECIMAL(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.ButtonSecondary(
    symbol = ".",
    modifier = modifier,
    onClick = onClick
)

@Composable
internal fun Buttons.EQUAL(modifier: Modifier = Modifier, onClick: () -> Unit) = Buttons.Button(
    symbol = "=",
    modifier = modifier,
    onClick = onClick
)