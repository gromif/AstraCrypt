package com.nevidimka655.astracrypt.domain.room.model

import com.nevidimka655.astracrypt.data.room.StorageItemState
import com.nevidimka655.astracrypt.data.room.StorageItemType

data class StorageItem(
    val id: Long = 0,
    val parent: Long = 0,
    val name: String = "",
    val preview: DatabasePreview? = null,
    val file: DatabaseFile? = null,
    val size: Long = 0,
    val state: StorageItemState = StorageItemState.Default,
    val itemType: StorageItemType = StorageItemType.Other,
    val time: DatabaseTime? = null
)