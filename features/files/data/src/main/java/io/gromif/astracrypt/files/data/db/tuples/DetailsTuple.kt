package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo
import io.gromif.astracrypt.files.domain.model.FileType

data class DetailsTuple(
    val name: String,
    val type: FileType,

    val file: String?,
    @ColumnInfo(name = "file-aead") val fileAead: Int,

    val preview: String?,
    @ColumnInfo(name = "preview-aead") val previewAead: Int,

    val flags: String?,
    @ColumnInfo(name = "time") val time: Long,
    val size: Long,
)