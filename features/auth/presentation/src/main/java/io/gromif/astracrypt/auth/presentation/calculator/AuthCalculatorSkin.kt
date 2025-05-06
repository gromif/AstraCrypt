package io.gromif.astracrypt.auth.presentation.calculator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.gromif.calculator.CalculatorScreen
import io.gromif.ui.compose.core.ext.FlowObserver

@Composable
fun AuthCalculatorSkin(
    modifier: Modifier = Modifier,
    onValidate: (Boolean) -> Unit
) {
    val vm: AuthCalculatorSkinViewModel = hiltViewModel()

    FlowObserver(vm.resultFlow) {
        onValidate(it)
    }

    CalculatorScreen(
        modifier = modifier,
        onCalculate = vm::verifySkin
    )
}