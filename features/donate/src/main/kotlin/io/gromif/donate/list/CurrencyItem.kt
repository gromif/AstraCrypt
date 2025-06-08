package io.gromif.donate.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.donate.model.Currency
import io.gromif.ui.compose.core.FilledTonalIconButton

@Preview(showBackground = true)
@Composable
internal fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: Currency = Currency(),
    onCopy: () -> Unit = {}
) = OutlinedCard(modifier = modifier) {
    ListItem(
        modifier = Modifier.clickable(onClick = onCopy),
        leadingContent = {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(currency.iconResId),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                text = currency.name,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(text = currency.address)
        },
        trailingContent = {
            FilledTonalIconButton(icon = Icons.Default.ContentCopy, onClick = onCopy)
        }
    )
}
