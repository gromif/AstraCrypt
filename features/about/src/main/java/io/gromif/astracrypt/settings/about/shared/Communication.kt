package io.gromif.astracrypt.settings.about.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.gromif.ui.compose.core.OneLineListItem

@Composable
internal fun CommunicationOptions(
    storeName: String,
    onEmailClick: () -> Unit,
    onMarketClick: () -> Unit,
) = Card(modifier = Modifier.fillMaxWidth()) {
    OneLineListItem(titleText = "Email", onClick = onEmailClick)
    OneLineListItem(titleText = storeName, onClick = onMarketClick)
}