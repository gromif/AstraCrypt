package io.gromif.ui.compose.core.banners

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.ui.compose.core.theme.spaces

@Preview
@Composable
fun Banner.Note(
    imageVector: ImageVector = Icons.Default.Star,
    text: String = "SIMPLE_TEXT_TOOLTIP",
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    iconTintColor: Color = MaterialTheme.colorScheme.onTertiaryContainer
) = Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = containerColor)
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {}.padding(MaterialTheme.spaces.spaceMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = iconTintColor
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = text
        )
    }
}