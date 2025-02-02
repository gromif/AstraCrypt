package io.gromif.astracrypt.files.data.db.tuples

import io.gromif.astracrypt.files.domain.model.ItemType

data class ExportTuple(
    val id: Long,
    val name: String,
    val type: ItemType,
    val file: String?,
    val fileAead: Int,
)