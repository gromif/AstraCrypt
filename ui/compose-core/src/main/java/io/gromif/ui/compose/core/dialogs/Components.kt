package io.gromif.ui.compose.core.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import io.gromif.ui.compose.core.ButtonWithIcon
import io.gromif.ui.compose.core.OutlinedButtonWithIcon
import io.gromif.ui.compose.core.TextButton
import io.gromif.ui.compose.core.dialogs.DialogDefaults.titleCentered
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun Dialog(
    title: @Composable (() -> Unit)?,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)?,
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
    content: @Composable (() -> Unit)
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        title = title,
        text = content,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
            securePolicy = securePolicy
        )
    )
}

@Composable
fun DialogWithNewButtonBar(
    title: String = "TEST FIELD DIALOG",
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: () -> Unit = {},
    isConfirmButtonEnabled: Boolean = true,
    buttonBar: @Composable () -> Unit = DialogDefaults.newButtonBar(
        onConfirmClick = onConfirmRequest,
        onDismissClick = onDismissRequest,
        isConfirmButtonEnabled = isConfirmButtonEnabled
    ),
    content: @Composable () -> Unit,
) {
    Dialog(
        title = titleCentered(title = title),
        content = content,
        confirmButton = buttonBar,
        dismissButton = null,
        onDismissRequest = onDismissRequest
    )
}

object DialogDefaults {

    @Composable
    fun title(
        modifier: Modifier = Modifier,
        textAlign: TextAlign? = null,
        title: String
    ): @Composable (() -> Unit) = {
        Text(modifier = modifier, text = title, textAlign = textAlign)
    }

    @Composable
    fun titleCentered(title: String) = title(
        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, title = title
    )

    @Composable
    fun contentRootScrollable(
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
            MaterialTheme.spaces.spaceMedium, Alignment.Top
        ),
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        content: @Composable ColumnScope.() -> Unit
    ): @Composable (() -> Unit) = {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement,
            horizontalAlignment,
            content
        )
    }

    @Composable
    fun contentText(text: String) = contentRootScrollable {
        Text(text)
    }

    @Composable
    fun textButton(
        modifier: Modifier = Modifier,
        title: String,
        enabled: Boolean = true,
        onClick: () -> Unit
    ): @Composable (() -> Unit) = {
        TextButton(modifier, title, enabled, onClick)
    }

    @Composable
    fun newButtonBar(
        onConfirmClick: () -> Unit,
        onDismissClick: () -> Unit,
        isConfirmButtonEnabled: Boolean = false,
        confirmText: String = stringResource(id = android.R.string.ok),
        confirmIcon: ImageVector = Icons.Default.Done,
        dismissText: String = stringResource(id = android.R.string.cancel),
        dismissIcon: ImageVector = Icons.Default.Close
    ): @Composable (() -> Unit) = {
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall)) {
            OutlinedButtonWithIcon(
                modifier = Modifier.weight(1f),
                icon = dismissIcon,
                title = dismissText,
                onClick = onDismissClick
            )
            ButtonWithIcon(
                modifier = Modifier.weight(1f),
                enabled = isConfirmButtonEnabled,
                icon = confirmIcon,
                title = confirmText,
                onClick = onConfirmClick
            )
        }
    }

}