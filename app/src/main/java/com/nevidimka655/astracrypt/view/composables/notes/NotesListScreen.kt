package com.nevidimka655.astracrypt.view.composables.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.nevidimka655.astracrypt.view.composables.components.NoItemsPage
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.List
import com.nevidimka655.notes.ui.Note

@Composable
fun NotesListScreen(
    noteItems: LazyPagingItems<Notes.Item>,
    showEmptyPage: Boolean,
    onClick: (id: Long) -> Unit
) {
    if (showEmptyPage) NoItemsPage(
        mainIcon = Icons.Filled.Description,
        actionIcon = Icons.AutoMirrored.Default.NoteAdd
    ) else Notes.List {
        items(
            count = noteItems.itemSnapshotList.size,
            key = { noteItems[it]?.id ?: it }
        ) { index ->
            noteItems[index]?.let {
                Notes.Note(
                    title = it.title,
                    summary = it.textPreview,
                    date = it.creationTime
                ) {
                    onClick(it.id)
                }
            }
        }
    }
}