package io.gromif.notes.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "text") val text: String? = null,
    @ColumnInfo(name = "text_preview") val textPreview: String? = null,
    @ColumnInfo(name = "category_id") val categoryId: Long = 0,
    @ColumnInfo(name = "state") val state: Int = 0,
    @ColumnInfo(name = "time_c") val creationTime: Long = 0
)