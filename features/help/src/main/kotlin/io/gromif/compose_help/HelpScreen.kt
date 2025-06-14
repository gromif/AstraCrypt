package io.gromif.compose_help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.ui.compose.core.PreferencesGroupTitle
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun HelpScreen(modifier: Modifier = Modifier, list: List<HelpItem>) = Column(
    modifier = modifier.padding(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
) {
    for ((header, text) in list) {
        HelpHeaderText(text = stringResource(id = header))
        HelpText(text = stringResource(id = text))
    }
}

@Preview(showBackground = true)
@Composable
private fun HelpText(text: String = "Test text") = SelectionContainer { Text(text = text) }

@Preview(showBackground = true)
@Composable
private fun HelpHeaderText(text: String = "Test header") = PreferencesGroupTitle(text)