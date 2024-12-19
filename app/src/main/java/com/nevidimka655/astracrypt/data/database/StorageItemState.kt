package com.nevidimka655.astracrypt.data.database

enum class StorageItemState {
    Default, Deleted, Starred;

    val isDefault get() = this == Default
    val isStarred get() = this == Starred

}