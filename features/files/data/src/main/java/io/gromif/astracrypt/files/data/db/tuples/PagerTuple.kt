package io.gromif.astracrypt.files.data.db.tuples

import io.gromif.astracrypt.files.domain.model.ItemType

data class PagerTuple(
    val id: Long,
    val name: String,
    val type: ItemType,
    val preview: String?,
    val previewAead: Int,
    val state: Int
)