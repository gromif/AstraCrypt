package com.nevidimka655.astracrypt.view.composables.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.nevidimka655.astracrypt.view.composables.shared.NoItemsPage
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.List
import com.nevidimka655.notes.ui.Note

@Composable
fun NotesListScreen(
    noteItems: LazyPagingItems<Notes.Item>,
    showEmptyPage: Boolean
) {
    if (showEmptyPage) NoItemsPage(
        mainIcon = Icons.Filled.Description,
        actionIcon = Icons.Filled.Edit
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
                    /*lifecycleScope.launch {
                        notesManager.preloadText(
                            noteId = it.id,
                            title = it.title
                        )
                    }
                    showNote(title = it.title)*/
                }
            }
        }
    }
}