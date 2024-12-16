package com.nevidimka655.astracrypt.view.composables.files.sheets


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.icons.FileType
import com.nevidimka655.astracrypt.app.theme.icons.Photo
import com.nevidimka655.ui.compose_core.FilledTonalIconButton
import com.nevidimka655.ui.compose_core.OneLineListItem
import com.nevidimka655.ui.compose_core.sheets.SheetDefaults
import com.nevidimka655.ui.compose_core.sheets.default
import com.nevidimka655.ui.compose_core.theme.spaces

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun filesOptionsSheet(
    state: MutableState<Boolean> = mutableStateOf(false),
    name: String = "Test",
    itemIcon: ImageVector = Icons.FileType.Photo,
    isFolder: Boolean = true,
    isStarred: Boolean = false,
    sheetState: SheetState = SheetDefaults.state(),
    onOpen: () -> Unit = {},
    onExport: () -> Unit = {},
    onRename: () -> Unit = {},
    onDelete: () -> Unit = {},
    onDetails: () -> Unit = {},
    onStarStateChange: (Boolean) -> Unit = {}
) = SheetDefaults.default(
    state = state, sheetState = sheetState
) {
    val context = LocalContext.current
    Column {
        SheetFilesOptionsHeader(
            text = name,
            itemIcon = itemIcon,
            isFolder = isFolder,
            onRename = onRename,
            onDelete = onDelete
        )
        HorizontalDivider()
        SheetFilesOptionsItem(
            text = context.getString(R.string.open),
            imageVector = Icons.AutoMirrored.Default.OpenInNew,
            onClick = onOpen
        )
        SheetFilesOptionsItem(
            text = context.getString(R.string.files_options_export),
            imageVector = Icons.Outlined.SaveAlt,
            onClick = onExport
        )
        SheetFilesOptionsItem(
            text = if (isStarred) {
                context.getString(R.string.files_options_removeFromStarred)
            } else context.getString(R.string.files_options_addToStarred),
            imageVector = if (isStarred) Icons.Default.Star else Icons.Outlined.StarOutline,
            onClick = { onStarStateChange(!isStarred) }
        )
        SheetFilesOptionsItem(
            text = context.getString(R.string.files_options_select),
            imageVector = Icons.Outlined.SelectAll
        ) {

        }
        SheetFilesOptionsItem(
            text = context.getString(R.string.files_options_details),
            imageVector = Icons.Outlined.Info,
            onClick = onDetails
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SheetFilesOptionsHeader(
    modifier: Modifier = Modifier,
    text: String = "Test",
    itemIcon: ImageVector = Icons.FileType.Photo,
    isFolder: Boolean = true,
    onRename: () -> Unit = {},
    onDelete: () -> Unit = {}
) = Column(
    modifier = Modifier.padding(
        bottom = MaterialTheme.spaces.spaceMedium,
        start = MaterialTheme.spaces.spaceMedium,
        end = MaterialTheme.spaces.spaceMedium
    ),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spaces.spaceLarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        FilledTonalIconButton(
            onClick = onDelete,
            icon = Icons.Outlined.DeleteForever,
            contentDescription = context.getString(R.string.files_options_delete)
        )
        Icon(
            imageVector = itemIcon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = if (isFolder) MaterialTheme.colorScheme.onSurfaceVariant else Color.Unspecified
        )
        FilledTonalIconButton(
            onClick = onRename,
            icon = Icons.Outlined.Edit,
            contentDescription = context.getString(R.string.files_options_rename)
        )
    }
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
private fun SheetFilesOptionsItem(
    modifier: Modifier = Modifier,
    text: String = "TEST",
    imageVector: ImageVector = Icons.Default.Add,
    contentDescription: String = text,
    onClick: (() -> Unit)? = {}
) = OneLineListItem(
    modifier = modifier,
    titleText = text,
    leadingContent = { Icon(imageVector, contentDescription) },
    onClick = onClick
)