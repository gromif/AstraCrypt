package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import io.gromif.astracrypt.files.domain.model.FileType

data class ExportTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: FileType,
    @ColumnInfo(name = "file") val file: String?,
    @ColumnInfo(name = "file-aead") val fileAead: Int,
)