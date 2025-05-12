package io.gromif.ui.compose.core.text_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.KeyboardType

@Stable
val _keyboardOptionsPassword = KeyboardOptions.Default.copy(
    autoCorrectEnabled = false,
    keyboardType = KeyboardType.Password
)

val KeyboardOptions.Password get() = _keyboardOptionsPassword