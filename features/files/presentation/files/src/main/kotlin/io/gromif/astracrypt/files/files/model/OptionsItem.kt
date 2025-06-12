package io.gromif.astracrypt.files.files.model

import android.os.Parcelable
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class OptionsItem(
    val id: Long = -1,
    val name: String = "Folder",
    val isStarred: Boolean = false,
    val itemType: ItemType = ItemType.Folder,
    val isFolder: Boolean = itemType == ItemType.Folder
) : Parcelable
