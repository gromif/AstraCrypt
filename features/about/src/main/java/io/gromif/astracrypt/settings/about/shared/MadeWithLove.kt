package io.gromif.astracrypt.settings.about.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.settings.about.R

@Preview(showBackground = true)
@Composable
internal fun MadeWithLove(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.msg_madeWithLove),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )
}
