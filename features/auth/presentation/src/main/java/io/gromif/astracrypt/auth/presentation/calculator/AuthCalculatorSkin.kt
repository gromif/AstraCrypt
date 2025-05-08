package io.gromif.astracrypt.auth.presentation.calculator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.gromif.calculator.CalculatorScreen

@Composable
fun AuthCalculatorSkin(
    modifier: Modifier = Modifier
) {
    val vm: AuthCalculatorSkinViewModel = hiltViewModel()

    CalculatorScreen(
        modifier = modifier,
        onCalculate = vm::verifySkin
    )
}