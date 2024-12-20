package com.nevidimka655.astracrypt.view.composables.notes.overview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity
import com.nevidimka655.astracrypt.domain.usecase.notes.CreateNewNoteUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.DeleteByIdUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.LoadNoteByIdUseCase
import com.nevidimka655.astracrypt.domain.usecase.notes.UpdateNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STATE_ID = "id"
private const val STATE_NAME = "name"
private const val STATE_TEXT = "value"

@HiltViewModel
class OverviewNoteViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val createNewNoteUseCase: CreateNewNoteUseCase,
    private val loadNoteByIdUseCase: LoadNoteByIdUseCase,
    private val updateNoteByIdUseCase: UpdateNoteByIdUseCase,
    private val deleteByIdUseCase: DeleteByIdUseCase
) : ViewModel() {
    private val idState = state.getStateFlow(STATE_ID, -1L)
    val nameState = state.getStateFlow(STATE_NAME, "")
    val textState = state.getStateFlow(STATE_TEXT, "")

    fun setName(name: String) {
        state[STATE_NAME] = name
    }

    fun setText(text: String) {
        state[STATE_TEXT] = text
    }

    suspend fun load(id: Long) {
        if (idState.value == -1L) {
            state[STATE_ID] = id
            val note: NoteItemEntity = loadNoteByIdUseCase(id = id)
            setName(name = note.name ?: "")
            setText(text = note.text ?: "")
        }
    }

    fun delete() = viewModelScope.launch(defaultDispatcher) {
        deleteByIdUseCase(id = idState.value)
    }

    fun save() = viewModelScope.launch(defaultDispatcher) {
        if (idState.value != -1L) {
            updateNoteByIdUseCase(
                id = idState.value,
                name = nameState.value,
                text = textState.value
            )
        } else {
            createNewNoteUseCase(
                name = nameState.value,
                text = textState.value
            )
        }
    }

}