package io.gromif.astracrypt.files.files.model

data class FilesInitialParams(
    val startParentId: Long? = null,
    val startParentName: String = "",
    val isStarred: Boolean,
)
