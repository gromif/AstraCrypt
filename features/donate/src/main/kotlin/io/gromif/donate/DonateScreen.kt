package io.gromif.donate

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.donate.list.CurrencyList
import io.gromif.donate.model.Currencies
import kotlinx.coroutines.launch

@Preview
@Composable
fun DonateScreen(modifier: Modifier = Modifier) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    CurrencyList(
        modifier = modifier,
        currencies = Currencies.default
    ) {
        scope.launch {
            val clipData = ClipData.newPlainText(it.name, it.address)
            val clipEntry = ClipEntry(clipData)
            clipboard.setClipEntry(clipEntry)
        }
    }
}
