package io.gromif.calculator.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Buttons.ZERO(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "0", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.ONE(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "1", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.TWO(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "2", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.THREE(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "3", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.FOUR(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "4", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.FIVE(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "5", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.SIX(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "6", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.SEVEN(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "7", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.EIGHT(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "8", modifier = modifier, onClick = onClick)

@Composable
internal fun Buttons.NINE(modifier: Modifier = Modifier, onClick: () -> Unit) =
    Buttons.ButtonSecondary(symbol = "9", modifier = modifier, onClick = onClick)