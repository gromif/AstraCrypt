package com.nevidimka655.notes.data.database.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class TransformNotesTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "text_preview") val textPreview: String?
)