package io.gromif.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.gromif.astracrypt.calculator.domain.Action
import io.gromif.astracrypt.calculator.domain.Operation
import io.gromif.astracrypt.calculator.domain.State
import io.gromif.calculator.buttons.AC
import io.gromif.calculator.buttons.ADD
import io.gromif.calculator.buttons.Buttons
import io.gromif.calculator.buttons.DECIMAL
import io.gromif.calculator.buttons.DEL
import io.gromif.calculator.buttons.DIVIDE
import io.gromif.calculator.buttons.EIGHT
import io.gromif.calculator.buttons.EQUAL
import io.gromif.calculator.buttons.FIVE
import io.gromif.calculator.buttons.FOUR
import io.gromif.calculator.buttons.MULTIPLY
import io.gromif.calculator.buttons.NINE
import io.gromif.calculator.buttons.ONE
import io.gromif.calculator.buttons.SEVEN
import io.gromif.calculator.buttons.SIX
import io.gromif.calculator.buttons.SUBTRACT
import io.gromif.calculator.buttons.THREE
import io.gromif.calculator.buttons.TWO
import io.gromif.calculator.buttons.ZERO
import io.gromif.ui.compose.core.ext.LocalWindowHeight
import io.gromif.ui.compose.core.ext.isCompact
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    onCalculate: (String) -> Unit
) {
    val vm: CalculatorViewModel = viewModel()

    Screen(
        modifier = modifier,
        state = vm.state,
        onAction = {
            if (it is Action.Calculate) onCalculate(vm.state.number1)
            vm.onAction(it)
        }
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    state: State = State(),
    onAction: (Action) -> Unit = {}
) {
    if (LocalWindowHeight.current.isCompact) ScreenHorizontal(modifier, state, onAction)
    else ScreenVertical(modifier, state, onAction)
}

@Composable
private fun StateBox(
    modifier: Modifier = Modifier,
    state: State,
    maxLines: Int = 3
) = SelectionContainer(modifier = modifier) {
    Text(
        text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
        fontWeight = FontWeight.Light,
        fontSize = 52.sp,
        color = MaterialTheme.colorScheme.onSurface,
        lineHeight = 52.sp,
        textAlign = TextAlign.End,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenVertical(
    modifier: Modifier = Modifier,
    state: State = State(),
    onAction: (Action) -> Unit = {}
) = Column(
    modifier = modifier.padding(MaterialTheme.spaces.spaceMedium),
    Arrangement.Bottom
) {
    val defaultModifier = Modifier
        .aspectRatio(1f)
        .weight(1f)
    val buttonSpacing = MaterialTheme.spaces.spaceSmall
    StateBox(modifier = Modifier.align(Alignment.End), state = state)
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        @Composable
        fun CustomRow(content: @Composable (RowScope.() -> Unit)) = Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
            content = content
        )

        val specialModifier = Modifier
            .aspectRatio(2f)
            .weight(2f)
        CustomRow {
            Buttons.AC(specialModifier) { onAction(Action.Clear) }
            Buttons.DEL(specialModifier) { onAction(Action.Delete) }
            Buttons.DIVIDE(defaultModifier) { onAction(Action.Operation(Operation.Divide)) }
        }
        CustomRow {
            Buttons.SEVEN(defaultModifier) { onAction(Action.Number(7)) }
            Buttons.EIGHT(defaultModifier) { onAction(Action.Number(8)) }
            Buttons.NINE(defaultModifier) { onAction(Action.Number(9)) }
            Buttons.MULTIPLY(defaultModifier) { onAction(Action.Operation(Operation.Multiply)) }
        }
        CustomRow {
            Buttons.FOUR(defaultModifier) { onAction(Action.Number(4)) }
            Buttons.FIVE(defaultModifier) { onAction(Action.Number(5)) }
            Buttons.SIX(defaultModifier) { onAction(Action.Number(6)) }
            Buttons.SUBTRACT(defaultModifier) { onAction(Action.Operation(Operation.Subtract)) }
        }
        CustomRow {
            Buttons.ONE(defaultModifier) { onAction(Action.Number(1)) }
            Buttons.TWO(defaultModifier) { onAction(Action.Number(2)) }
            Buttons.THREE(defaultModifier) { onAction(Action.Number(3)) }
            Buttons.ADD(defaultModifier) { onAction(Action.Operation(Operation.Add)) }
        }
        CustomRow {
            Buttons.ZERO(specialModifier) { onAction(Action.Number(0)) }
            Buttons.DECIMAL(defaultModifier) { onAction(Action.Decimal) }
            Buttons.EQUAL(defaultModifier) { onAction(Action.Calculate) }
        }
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_7_pro,orientation=landscape")
@Composable
internal fun ScreenHorizontal(
    modifier: Modifier = Modifier,
    state: State = State(),
    onAction: (Action) -> Unit = {}
) = Column(modifier = modifier.padding(MaterialTheme.spaces.spaceMedium)) {
    val rowModifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    Box(modifier = rowModifier) {
        StateBox(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            state = state,
            maxLines = 1
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(2f),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall)
    ) {
        @Composable
        fun CustomRow(content: @Composable (RowScope.() -> Unit)) = Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall),
            content = content
        )

        CustomRow {
            CustomRow {
                Buttons.AC(Modifier.weight(1f)) { onAction(Action.Clear) }
                Buttons.DEL(Modifier.weight(1f)) { onAction(Action.Delete) }
            }
            CustomRow {
                Buttons.DIVIDE(Modifier.weight(1f)) { onAction(Action.Operation(Operation.Divide)) }
                Buttons.MULTIPLY(Modifier.weight(1f)) { onAction(Action.Operation(Operation.Multiply)) }
            }
            CustomRow {
                Buttons.SUBTRACT(Modifier.weight(1f)) { onAction(Action.Operation(Operation.Subtract)) }
                Buttons.ADD(Modifier.weight(1f)) { onAction(Action.Operation(Operation.Add)) }
            }
        }
        CustomRow {
            Buttons.SEVEN(Modifier.weight(1f)) { onAction(Action.Number(7)) }
            Buttons.EIGHT(Modifier.weight(1f)) { onAction(Action.Number(8)) }
            Buttons.NINE(Modifier.weight(1f)) { onAction(Action.Number(9)) }
        }
        CustomRow {
            Buttons.FOUR(Modifier.weight(1f)) { onAction(Action.Number(4)) }
            Buttons.FIVE(Modifier.weight(1f)) { onAction(Action.Number(5)) }
            Buttons.SIX(Modifier.weight(1f)) { onAction(Action.Number(6)) }
        }
        CustomRow {
            CustomRow {
                Buttons.ZERO(Modifier.weight(1f)) { onAction(Action.Number(0)) }
                Buttons.ONE(Modifier.weight(1f)) { onAction(Action.Number(1)) }
            }
            CustomRow {
                Buttons.TWO(Modifier.weight(1f)) { onAction(Action.Number(2)) }
                Buttons.THREE(Modifier.weight(1f)) { onAction(Action.Number(3)) }
            }
            CustomRow {
                Buttons.DECIMAL(Modifier.weight(1f)) { onAction(Action.Decimal) }
                Buttons.EQUAL(Modifier.weight(1f)) { onAction(Action.Calculate) }
            }
        }
    }
}