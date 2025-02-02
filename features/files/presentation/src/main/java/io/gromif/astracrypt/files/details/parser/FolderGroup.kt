package io.gromif.astracrypt.files.details.parser

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.compose_details.addGroup
import com.nevidimka655.compose_details.addItem
import com.nevidimka655.compose_details.model.DetailsGroup
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

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