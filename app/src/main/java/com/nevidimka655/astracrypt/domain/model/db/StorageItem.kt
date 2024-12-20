package com.nevidimka655.astracrypt.domain.model.db

import com.nevidimka655.astracrypt.data.database.StorageItemType

data class StorageItem(
    val id: Long = 0,
    val parent: Long = 0,
    val name: String = "",
    val preview: StorageFile? = null,
    val file: StorageFile? = null,
    val size: Long = 0,
    val state: StorageState = StorageState.Default,
    val itemType: StorageItemType = StorageItemType.Other,
    val time: StorageFileTime? = null
)