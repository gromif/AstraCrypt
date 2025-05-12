package io.gromif.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.usecase.GetNotesListFlow
import javax.inject.Inject

@HiltViewModel
internal class NotesListViewModel @Inject constructor(
    getNotesListFlow: GetNotesListFlow<PagingData<Note>>
) : ViewModel() {
    val notesPaging = getNotesListFlow().cachedIn(viewModelScope)

}