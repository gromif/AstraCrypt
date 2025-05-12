package io.gromif.ui.compose.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.ui.compose.core.theme.spaces

@Preview
@Composable
private fun InfoTitleCardPreview() = CardWithTitle(modifier = Modifier.height(250.dp))

@Composable
inline fun CardWithTitle(
    modifier: Modifier = Modifier,
    modifierColumn: Modifier = Modifier,
    titleText: String = "DUMMY",
    verticalArrangement: Arrangement.Vertical? = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
    crossinline content: @Composable (ColumnScope.() -> Unit) = {}
) = ElevatedCard(
    modifier = modifier.fillMaxWidth()
) {
    Column(
        modifier = modifierColumn.fillMaxWidth().padding(MaterialTheme.spaces.spaceMedium),
        verticalArrangement = verticalArrangement ?: Arrangement.Top
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        content(this)
    }
}