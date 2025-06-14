package io.gromif.ui.compose.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

object TextFields {

    object Icons

    @Composable
    fun textFieldValue(text: String = "", selectAll: Boolean = false) = rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = if (selectAll) TextRange(0, text.length) else TextRange(text.length)
            )
        )
    }

    @Composable
    fun placeholder(text: String) = @Composable { Text(text = text) }

    @Composable
    fun label(text: String) = @Composable { Text(text = text) }

    @Composable
    fun supportingCounter(length: Int, maxLength: Int) = @Composable {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$length/$maxLength",
            textAlign = TextAlign.End
        )
    }

    @Composable
    fun supporting(text: String) = @Composable { Text(text = text) }

    @Composable
    fun passwordVisualTransform(state: Boolean) = if (!state) {
        PasswordVisualTransformation()
    } else VisualTransformation.None

}