package io.gromif.astracrypt.files.data.db.tuples

import io.gromif.astracrypt.files.domain.model.FileType

data class DetailsTuple(
    val name: String,
    val type: FileType,
    val file: String?,
    val fileAead: Int,
    val preview: String?,
    val previewAead: Int,
    val flags: String?,
    val time: Long,
    val size: Long,
)