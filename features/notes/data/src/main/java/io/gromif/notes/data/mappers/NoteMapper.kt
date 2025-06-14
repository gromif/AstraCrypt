package io.gromif.notes.data.mappers

import android.text.format.DateFormat
import io.gromif.astracrypt.utils.Mapper
import io.gromif.notes.data.db.NoteItemEntity
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.model.NoteState

private const val TIME_PATTERN = "d MMMM yyyy"

class NoteMapper: Mapper<NoteItemEntity, Note> {

    override fun invoke(item: NoteItemEntity): Note {
        return Note(
            id = item.id,
            name = item.name ?: "",
            text = item.text ?: "",
            textPreview = item.textPreview?.run { "$this..." } ?: "",
            state = NoteState.entries[item.state],
            creationTime = DateFormat.format(TIME_PATTERN, item.creationTime).toString()
        )
    }

}