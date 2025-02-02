package io.gromif.astracrypt.files.data.db.tuples

data class DatabaseTransformTuple(
    val id: Long,
    val name: String,
    val preview: String,
    val file: String,
    val flags: String
)