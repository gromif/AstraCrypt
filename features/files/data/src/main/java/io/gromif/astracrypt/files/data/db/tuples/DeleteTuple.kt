package io.gromif.astracrypt.files.data.db.tuples

data class DeleteTuple(
    val id: Long,
    val file: String?,
    val preview: String?
)