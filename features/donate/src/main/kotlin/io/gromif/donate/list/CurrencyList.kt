package io.gromif.donate.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.donate.model.Currencies
import io.gromif.donate.model.Currency
import io.gromif.ui.compose.core.theme.spaces

@Preview(showSystemUi = false, showBackground = true)
@Composable
internal fun CurrencyList(
    modifier: Modifier = Modifier,
    currencies: List<Currency> = Currencies.default,
    onCurrencyClick: (Currency) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(300.dp),
        contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        items(currencies) {
            CurrencyItem(currency = it) {
                onCurrencyClick(it)
            }
        }
    }
}
