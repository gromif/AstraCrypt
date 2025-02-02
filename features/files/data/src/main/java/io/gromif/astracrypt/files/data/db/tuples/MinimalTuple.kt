package io.gromif.astracrypt.files.data.db.tuples

data class MinimalTuple(
    val id: Long,
    val name: String,
    val file: String?,
    val preview: String?
)