package io.gromif.astracrypt.files.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.model.FileType

@Entity(tableName = "store_items")
data class FilesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "parent")
    val parent: Long = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "state")
    val state: FileState = FileState.Default,

    @ColumnInfo(name = "type")
    val type: FileType = FileType.Folder,

    @ColumnInfo(name = "file")
    val file: String? = null,
    @ColumnInfo(name = "fileAead")
    val fileAead: Int = -1,

    @ColumnInfo(name = "preview")
    val preview: String? = null,
    @ColumnInfo(name = "previewAead")
    val previewAead: Int = -1,

    @ColumnInfo(name = "flags")
    val flags: String? = null,

    @ColumnInfo(name = "time")
    val time: Long = 0,

    @ColumnInfo(name = "size")
    val size: Long = 0,
)