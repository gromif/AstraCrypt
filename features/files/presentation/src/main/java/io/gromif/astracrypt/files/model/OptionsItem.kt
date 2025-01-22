package io.gromif.astracrypt.files.model

import android.os.Parcelable
import io.gromif.astracrypt.files.domain.model.FileType
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionsItem(
    val id: Long = -1,
    val name: String = "Folder",
    val isStarred: Boolean = false,
    val fileType: FileType = FileType.Folder,
    val isFolder: Boolean = fileType == FileType.Folder
): Parcelable