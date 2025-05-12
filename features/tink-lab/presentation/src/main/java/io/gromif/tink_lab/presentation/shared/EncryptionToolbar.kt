package io.gromif.tink_lab.presentation.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NoEncryption
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun EncryptionToolbar(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall),
    onEncrypt: () -> Unit = {},
    onDecrypt: () -> Unit = {}
) = Row(
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
) {
    ToolbarButton(
        imageVector = Icons.Default.Lock,
        text = stringResource(id = R.string.encrypt),
        modifier = Modifier.weight(0.5f),
        onClick = onEncrypt
    )
    ToolbarButton(
        imageVector = Icons.Default.NoEncryption,
        text = stringResource(id = R.string.decrypt),
        modifier = Modifier.weight(0.5f),
        onClick = onDecrypt
    )
}