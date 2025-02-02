package io.gromif.astracrypt.files.data.db.tuples

import io.gromif.astracrypt.files.domain.model.FileType

data class ExportTuple(
    val id: Long,
    val name: String,
    val type: FileType,
    val file: String?,
    val fileAead: Int,
)