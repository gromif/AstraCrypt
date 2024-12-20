package com.nevidimka655.astracrypt.data.mappers

import android.text.format.DateFormat
import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity
import com.nevidimka655.astracrypt.domain.model.db.Note
import com.nevidimka655.astracrypt.domain.model.db.StorageState

private const val TIME_PATTERN = "d MMMM yyyy"

fun NoteItemEntity.toNote() = Note(
    name = name ?: "",
    text = text ?: "",
    textPreview = textPreview?.run { "$this..." } ?: "",
    state = StorageState.entries[state],
    creationTime = DateFormat.format(TIME_PATTERN, creationTime).toString()
)