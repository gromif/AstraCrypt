package io.gromif.astracrypt.files.data.db.tuples

data class UpdateAeadTuple(
    val id: Long,
    val name: String,
    val preview: String,
    val file: String,
    val flags: String
)