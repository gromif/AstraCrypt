package io.gromif.ui.compose.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import io.gromif.ui.compose.core.theme.spaces
import io.gromif.ui.haptic.Haptic

@Composable
fun PreferencesScreen(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        content = content
    )
}

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    titleText: String,
    summaryText: String? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    callback: () -> Unit
) = AutoLineListItem(
    modifier = modifier,
    titleText = titleText,
    summaryText = summaryText,
    leadingContent = leadingContent,
    trailingContent = trailingContent,
    onClick = callback
)

@Composable
fun PreferencesGroup(text: String? = null, content: @Composable ColumnScope.() -> Unit) {
    PreferencesGroupTitle(text = text)
    PreferencesGroupContent(content = content)
}

@Composable
fun ColumnScope.PreferencesGroupAnimated(
    text: String,
    isVisible: Boolean,
    content: @Composable ColumnScope.() -> Unit
) = AnimatedVisibility(modifier = Modifier.fillMaxWidth(), visible = isVisible) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        PreferencesGroupTitle(text = text)
        PreferencesGroupContent(content = content)
    }
}

@Composable
fun PreferencesGroupTitle(text: String? = null) {
    if (text != null) Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = MaterialTheme.spaces.spaceMedium),
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun PreferencesGroupContent(content: @Composable ColumnScope.() -> Unit) = Card(content = content)

@Composable
inline fun PreferencesSwitch(
    state: MutableState<Boolean>,
    titleText: String,
    summaryText: String? = null,
    isAutoSwitchState: Boolean = true,
    crossinline callback: (Boolean) -> Unit
) = PreferencesSwitch(
    isChecked = state.value,
    titleText = titleText,
    summaryText = summaryText,
    callback = {
        Haptic.toggle(state = it)
        if (isAutoSwitchState) state.value = it
        callback(it)
    }
)

@Composable
inline fun PreferencesSwitch(
    isChecked: Boolean,
    titleText: String,
    summaryText: String? = null,
    crossinline callback: (Boolean) -> Unit
) = AutoLineListItem(
    modifier = Modifier.toggleable(
        value = isChecked,
        role = Role.Switch,
        onValueChange = {
            Haptic.toggle(state = it)
            callback(it)
        }
    ),
    titleText = titleText,
    summaryText = summaryText,
    trailingContent = {
        Switch(checked = isChecked, onCheckedChange = null)
    }
)