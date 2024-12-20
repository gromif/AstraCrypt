package com.nevidimka655.astracrypt.view.composables.notes.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.domain.usecase.notes.CreateNewNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STATE_NAME = "name"
private const val STATE_TEXT = "value"

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val createNewNoteUseCase: CreateNewNoteUseCase
) : ViewModel() {
    val nameState = state.getStateFlow(STATE_NAME, "")
    val textState = state.getStateFlow(STATE_TEXT, "")

    fun setName(name: String) {
        state[STATE_NAME] = name
    }

    fun setText(text: String) {
        state[STATE_TEXT] = text
    }

    fun save() = viewModelScope.launch(defaultDispatcher) {
        createNewNoteUseCase(
            name = nameState.value,
            text = textState.value
        )
    }

}