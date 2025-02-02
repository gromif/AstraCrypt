package io.gromif.astracrypt.files.details.parser

import android.text.format.DateFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.SdCard
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.compose_details.addGroup
import com.nevidimka655.compose_details.addItem
import com.nevidimka655.compose_details.model.DetailsGroup
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.shared.title
import java.text.DecimalFormat

internal fun SnapshotStateList<DetailsGroup>.addDetailsGroup(
    type: FileType,
    details: ItemDetails
) = addGroup(
    name = TextWrap.Resource(R.string.files_options_details)
) {
    addItem(
        icon = Icons.AutoMirrored.Outlined.InsertDriveFile,
        title = TextWrap.Resource(R.string.itemType),
        summary = TextWrap.Resource(type.title)
    )
    if (details is ItemDetails.File) {
        addItem(
            icon = Icons.Outlined.FolderOpen,
            title = TextWrap.Resource(R.string.path),
            summary = TextWrap.Text(details.file.path)
        )
        val size = formatBytes(details.size)
        addItem(
            icon = Icons.Outlined.SdCard,
            title = TextWrap.Resource(id = R.string.currentSize),
            summary = TextWrap.Text(text = "$size (${details.size} B)")
        )
        val pattern = "d MMMM yyyy"
        val creationTime = DateFormat.format(pattern, details.creationTime).toString()
        addItem(
            icon = Icons.Outlined.AccessTime,
            title = TextWrap.Resource(id = R.string.creationTime),
            summary = TextWrap.Text(text = creationTime)
        )
    }
}

private fun formatBytes(length: Long): String {
    val kilobytes = length / 1024.0
    val megabytes = kilobytes / 1024.0
    val gigabytes = megabytes / 1024.0
    val terabytes = gigabytes / 1024.0
    val decimalFormat = DecimalFormat("0.00")
    return when {
        terabytes > 1 -> decimalFormat.format(terabytes) + " Tb"
        gigabytes > 1 -> decimalFormat.format(gigabytes) + " Gb"
        megabytes > 1 -> decimalFormat.format(megabytes) + " Mb"
        kilobytes > 1 -> decimalFormat.format(kilobytes) + " Kb"
        else -> decimalFormat.format(length) + " B"
    }
}