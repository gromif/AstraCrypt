package io.gromif.ui.compose.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.ui.compose.core.theme.spaces

@Preview(showBackground = true)
@Composable
fun HeaderPrimary(
    title: String = "TEST PRIMARY HEADER"
) = Header(
    title = title,
    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    color = MaterialTheme.colorScheme.primary
)

@Preview(showBackground = true)
@Composable
fun HeaderError(
    title: String = "TEST ERROR HEADER"
) = Header(
    title = title,
    backgroundColor = MaterialTheme.colorScheme.errorContainer,
    color = MaterialTheme.colorScheme.error
)

@Preview(showBackground = true)
@Composable
fun Header(
    title: String = "TEST HEADER",
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    color: Color = MaterialTheme.colorScheme.secondary
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(MaterialTheme.spaces.spaceSmall),
        text = title,
        color = color,
        fontWeight = FontWeight.Bold
    )
}