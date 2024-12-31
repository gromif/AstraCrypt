package com.nevidimka655.astracrypt.domain.model.db

enum class FileState {
    Default, Deleted, Starred;

    val isDefault get() = this == Default
    val isStarred get() = this == Starred

}