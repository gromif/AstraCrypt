package io.gromif.astracrypt.settings.privacy

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml

@Composable
internal fun Screen(
    modifier: Modifier = Modifier, privacyPolicyHtml: String
) = Column(modifier = modifier) {
    if (privacyPolicyHtml.isNotEmpty()) return@Column
    val parsedHtml = AnnotatedString.fromHtml(
        htmlString = privacyPolicyHtml,
        linkStyles = TextLinkStyles(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        ),
    )
    Text(parsedHtml)
}