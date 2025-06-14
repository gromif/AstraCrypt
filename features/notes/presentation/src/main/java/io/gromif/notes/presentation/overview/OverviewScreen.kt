package io.gromif.notes.presentation.overview

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.resources.R
import io.gromif.notes.presentation.Notes
import io.gromif.ui.compose.core.TextFields
import io.gromif.ui.compose.core.theme.spaces
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Notes.OverviewScreen(
    noteId: Long,
    editMode: Boolean,
    onSaveRequestChannel: Channel<Unit>,
    onDeleteRequestChannel: Channel<Unit>,
    onChangeName: (String) -> Unit,
    onChangeText: (String) -> Unit
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val vm: OverviewNoteViewModel = hiltViewModel()
    val name by vm.nameState.collectAsStateWithLifecycle()
    val text by vm.textState.collectAsStateWithLifecycle()

    LaunchedEffect(name, text) {
        onChangeName(name)
        onChangeText(text)
    }

    if (editMode) LaunchedEffect(Unit) {
        vm.load(id = noteId)
        onDeleteRequestChannel.receiveAsFlow().collectLatest {
            vm.delete().invokeOnCompletion { backDispatcher?.onBackPressed() }
        }
    }

    LaunchedEffect(Unit) {
        onSaveRequestChannel.receiveAsFlow().collectLatest {
            vm.save().invokeOnCompletion { backDispatcher?.onBackPressed() }
        }
    }

    Overview(
        name = name,
        onChangeName = { if (it.length <= 64) vm.setName(name = it) },
        text = text,
        onChangeText = { if (it.length <= 1000) vm.setText(text = it) }
    )
}

@Preview(showBackground = true)
@Composable
private fun Overview(
    name: String = "Note name",
    onChangeName: (String) -> Unit = {},
    text: String = "Note text",
    onChangeText: (String) -> Unit = {}
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxSize(),
        label = TextFields.label(text = stringResource(id = R.string.note_title)),
        value = name, onValueChange = onChangeName
    )
    OutlinedTextField(
        modifier = Modifier.fillMaxSize(),
        label = TextFields.label(text = stringResource(id = R.string.text)),
        value = text, onValueChange = onChangeText
    )
}