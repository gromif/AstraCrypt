package io.gromif.ui.compose.core.dialogs

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import io.gromif.ui.compose.core.Compose
import io.gromif.ui.compose.core.TextFields

@Composable
fun DialogsCore.TextFields.default(
    state: MutableState<Boolean> = Compose.state(),
    title: String,
    params: DialogsCore.TextFields.Params,
    onResult: (String) -> Unit
): MutableState<Boolean> {
    if (!state.value) return state

    var textValue by TextFields.textFieldValue(
        text = params.text,
        selectAll = params.selectAllText
    )
    DialogWithNewButtonBar(
        title = title,
        onDismissRequest = { state.value = false },
        onConfirmRequest = {
            state.value = false
            onResult(textValue.text)
        },
        isConfirmButtonEnabled = textValue.text.isNotBlank()
    ) {
        val focusRequester = FocusRequester()
        OutlinedTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = textValue,
            onValueChange = {
                if (it.text.length <= params.maxLength) textValue = it
            },
            supportingText = when {
                !params.supportingText.isNullOrBlank() -> TextFields.supporting(
                    text = params.supportingText
                )
                params.maxLength != Int.MAX_VALUE -> TextFields.supportingCounter(
                    length = textValue.text.length,
                    maxLength = params.maxLength
                )
                else -> null
            },
            singleLine = params.singleLine,
            label = params.label?.let { TextFields.label(text = it) },
            keyboardOptions = params.keyboardOptions
        )

        SideEffect { focusRequester.requestFocus() }
    }
    return state
}