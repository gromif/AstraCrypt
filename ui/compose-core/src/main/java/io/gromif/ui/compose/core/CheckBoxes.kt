package io.gromif.ui.compose.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun checkboxRounded(
    label: String,
    defaultState: Boolean = false,
    isShown: Boolean = true
): MutableState<Boolean> {
    val state = rememberSaveable { mutableStateOf(defaultState) }
    if (isShown) CheckboxRounded(label = label, state = state)
    return state
}

@Composable
fun CheckboxRounded(label: String, state: MutableState<Boolean>) =
    Surface(shape = CircleShape) { CheckBoxBase(label = label, state = state) }

@Composable
fun CheckBoxBase(label: String, state: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { state.value = !state.value }
            .padding(MaterialTheme.spaces.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = state.value,
            onCheckedChange = { state.value = it }
        )
        Text(
            modifier = Modifier,
            text = label
        )
    }
}