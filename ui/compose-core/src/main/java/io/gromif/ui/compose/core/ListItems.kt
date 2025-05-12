package io.gromif.ui.compose.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.ui.haptic.Haptic

@Composable
fun AutoLineListItem(
    modifier: Modifier = Modifier,
    titleText: String = "Some title here",
    summaryText: String? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    longClickCallback: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) = if (summaryText != null) TwoLineListItem(
    modifier = modifier,
    titleText = titleText,
    summaryText = summaryText,
    leadingContent = leadingContent,
    trailingContent = trailingContent,
    longClickCallback = longClickCallback,
    onClick = onClick
) else OneLineListItem(
    modifier = modifier,
    titleText = titleText,
    leadingContent = leadingContent,
    trailingContent = trailingContent,
    onClick = onClick,
    longClickCallback = longClickCallback
)

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun TwoLineListItem(
    modifier: Modifier = Modifier,
    titleText: String = "Some title here",
    summaryText: String = "Some summary here",
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    longClickCallback: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) = ListItem(
    modifier = modifier.run {
        when {
            longClickCallback != null && onClick != null -> combinedClickable(
                onClick = onClick, onLongClick = longClickCallback
            )
            onClick != null -> clickable(onClick = onClick)
            else -> this
        }
    },
    colors = ListItemDefaults.colors(containerColor = Color.Unspecified),
    headlineContent = { Text(text = titleText, style = MaterialTheme.typography.titleMedium) },
    supportingContent = { Text(summaryText) },
    leadingContent = leadingContent,
    trailingContent = trailingContent
)

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun OneLineListItem(
    modifier: Modifier = Modifier,
    titleText: String = "Some title here",
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    longClickCallback: (() -> Unit)? = null,
    onClick: (() -> Unit)? = {}
) = ListItem(
    modifier = modifier.run {
        when {
            longClickCallback != null && onClick != null -> combinedClickable(
                onClick = onClick, onLongClick = longClickCallback
            )
            onClick != null -> clickable(onClick = onClick)
            else -> this
        }
    },
    colors = ListItemDefaults.colors(containerColor = Color.Unspecified),
    headlineContent = { Text(text = titleText, style = MaterialTheme.typography.titleMedium) },
    trailingContent = trailingContent,
    leadingContent = leadingContent
)

@Preview
@Composable
fun RadioOneLineListItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    titleText: String = "Some title here",
    onClick: (() -> Unit)? = null
) = Row(
    modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp)
        .selectable(
            selected = isSelected,
            role = Role.RadioButton,
            onClick = {
                onClick?.invoke()
            }
        ),
    verticalAlignment = Alignment.CenterVertically
) {
    RadioButton(selected = isSelected, onClick = onClick)
    Text(text = titleText, style = MaterialTheme.typography.bodyLarge)
}

@Composable
inline fun CheckBoxOneLineListItem(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    titleText: String = "Some title here",
    crossinline onChange: ((Boolean) -> Unit)
) = OneLineListItem(
    modifier = modifier,
    titleText = titleText,
    leadingContent = {
        Checkbox(checked = isChecked, onCheckedChange = {
            Haptic.tick()
            onChange(it)
        })
    },
    onClick = {
        Haptic.tick()
        onChange(!isChecked)
    }
)