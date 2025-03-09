package io.gromif.astracrypt.files.files.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.astracrypt.files.files.model.RootInfo
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.theme.spaces

@Preview(showBackground = true)
@Composable
internal fun FilesBackStackList(
    rootBackStack: List<RootInfo> = listOf(
        RootInfo(name = "Root1"),
        RootInfo(name = "Root2"),
        RootInfo(name = "Root3"),
        RootInfo(name = "Root4")
    ),
    onClick: (index: Int?) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(rootBackStack.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = MaterialTheme.spaces.spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilesBackStackListItem(title = stringResource(id = R.string.home)) { onClick(null) }
        rootBackStack.forEachIndexed { i, it ->
            VerticalDivider(
                modifier = Modifier
                    .width(2.dp)
                    .height(20.dp)
            )
            FilesBackStackListItem(title = it.name) { onClick(i) }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun FilesBackStackListItem(
    title: String = "Folder", onClick: () -> Unit = {},
) = Text(
    modifier = Modifier
        .clip(CircleShape)
        .clickable(onClick = onClick)
        .padding(vertical = 13.dp, horizontal = 10.dp),
    text = title,
    color = MaterialTheme.colorScheme.onSurface,
    style = MaterialTheme.typography.titleSmall,
    maxLines = 1
)