package io.gromif.astracrypt.files.data.db.tuples

import io.gromif.astracrypt.files.domain.model.FileType

data class PagerTuple(
    val id: Long,
    val name: String,
    val type: FileType,
    val preview: String?,
    val previewAead: Int,
    val state: Int
)