package com.nevidimka655.domain.notes.model

enum class NoteState {
    Default, Deleted, Starred;

    val isDefault get() = this == Default
    val isDeleted get() = this == Deleted
    val isStarred get() = this == Starred

}