package io.gromif.astracrypt.files.details.parser

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.gromif.astracrypt.resources.R
import io.gromif.compose.details.addGroup
import io.gromif.compose.details.addItem
import io.gromif.compose.details.model.DetailsGroup
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun SnapshotStateList<DetailsGroup>.addFolderGroup(
    context: Context,
    filesCount: Int,
    foldersCount: Int,
) = addGroup(name = TextWrap.Resource(R.string.folder)) {
    val files = context.getString(R.string.files)
    val folders = context.getString(R.string.folders)
    val summaryText = "$files - $filesCount, $folders - $foldersCount"
    addItem(
        icon = Icons.Outlined.Folder,
        title = TextWrap.Resource(id = R.string.folderContents),
        summary = TextWrap.Text(text = summaryText)
    )
}