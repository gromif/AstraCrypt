package io.gromif.ui.compose.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.gromif.ui.compose.core.theme.spaces

@Preview(showBackground = true)
@Composable
fun BaseNoItemsPage(
    modifier: Modifier = Modifier,
    mainIcon: ImageVector = Icons.Outlined.FolderOpen,
    actionIcon: ImageVector = Icons.Default.Add,
    pageSize: NoItemsPageSize = NoItemsPageSize.FULL,
    title: String = "No items",
    summary: String = "empty"
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(vertical = MaterialTheme.spaces.spaceMedium)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .padding(pageSize.iconPadding)
                .size(pageSize.iconSize),
            imageVector = mainIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        val inlineIconId = remember { "action_add" }
        val annotatedString = remember {
            buildAnnotatedString {
                append(summary)
                appendInlineContent(inlineIconId, "[icon]")
            }
        }
        val inlineContentMap = mapOf(
            Pair(
                inlineIconId,
                InlineTextContent(
                    placeholder = Placeholder(
                        width = 24.sp,
                        height = 24.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    )
                ) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        )
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium,
            inlineContent = inlineContentMap,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoItemsPage(
    mainIcon: ImageVector = Icons.Outlined.FolderOpen,
    actionIcon: ImageVector = Icons.Default.Add,
    title: String = "No items",
    summary: String = "empty"
) = BaseNoItemsPage(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(MaterialTheme.spaces.spaceMedium),
    mainIcon = mainIcon,
    actionIcon = actionIcon,
    title = title,
    summary = summary
)

enum class NoItemsPageSize(
    val iconPadding: Dp,
    val iconSize: Dp
) {
    FULL(
        iconPadding = 20.dp,
        iconSize = 80.dp
    ),
    MEDIUM(
        iconPadding = 15.dp,
        iconSize = 60.dp
    )
}
