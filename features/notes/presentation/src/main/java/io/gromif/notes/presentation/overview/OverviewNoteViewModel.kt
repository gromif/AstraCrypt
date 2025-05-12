package io.gromif.notes.presentation.overview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.usecase.CreateUseCase
import io.gromif.notes.domain.usecase.DeleteByIdUseCase
import io.gromif.notes.domain.usecase.LoadByIdUseCase
import io.gromif.notes.domain.usecase.UpdateByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STATE_ID = "id"
private const val STATE_NAME = "name"
private const val STATE_TEXT = "value"

@HiltViewModel
internal class OverviewNoteViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val createUseCase: CreateUseCase,
    private val loadByIdUseCase: LoadByIdUseCase,
    private val updateByIdUseCase: UpdateByIdUseCase,
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
            val note: Note = loadByIdUseCase(id = id)
            setName(name = note.name)
            setText(text = note.text)
        }
    }

    fun delete() = viewModelScope.launch(defaultDispatcher) {
        deleteByIdUseCase(id = idState.value)
    }

    fun save() = viewModelScope.launch(defaultDispatcher) {
        if (idState.value != -1L) {
            updateByIdUseCase(
                id = idState.value,
                name = nameState.value,
                text = textState.value
            )
        } else {
            createUseCase(
                name = nameState.value,
                text = textState.value
            )
        }
    }

}