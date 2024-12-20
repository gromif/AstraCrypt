package com.nevidimka655.astracrypt.view.composables.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.theme.spaces

@Composable
fun BaseNoItemsPage(
    modifier: Modifier = Modifier,
    mainIcon: ImageVector = Icons.Outlined.FolderOpen,
    actionIcon: ImageVector = Icons.Default.Add,
    pageSize: NoItemsPageSize = NoItemsPageSize.FULL,
    title: Int = R.string.noItemsTitle,
    summary: Int = R.string.noItemsSummary
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
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        val inlineIconId = remember { "action_add" }
        val annotatedString = buildAnnotatedString {
            append(stringResource(id = summary))
            appendInlineContent(inlineIconId, "[icon]")
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

@Composable
fun NoItemsPage(
    mainIcon: ImageVector = Icons.Outlined.FolderOpen,
    actionIcon: ImageVector = Icons.Default.Add,
    title: Int = R.string.noItemsTitle,
    summary: Int = R.string.noItemsSummary
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
