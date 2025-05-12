package io.gromif.tink_lab.presentation.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import io.gromif.astracrypt.resources.R

@Composable
internal fun AssociatedDataTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) = OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    leadingIcon = { Icon(imageVector = Icons.Default.Dataset, null) },
    supportingText = {
        Text(
            text = "${value.length}",
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    },
    label = { Text(text = stringResource(id = R.string.associatedData)) },
    singleLine = true,
    modifier = modifier
)