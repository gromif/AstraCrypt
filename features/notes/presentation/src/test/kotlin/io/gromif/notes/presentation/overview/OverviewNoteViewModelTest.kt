package io.gromif.notes.presentation.overview

import androidx.lifecycle.SavedStateHandle
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.model.NoteState
import io.gromif.notes.domain.usecase.CreateUseCase
import io.gromif.notes.domain.usecase.DeleteByIdUseCase
import io.gromif.notes.domain.usecase.LoadByIdUseCase
import io.gromif.notes.domain.usecase.UpdateByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val STATE_ID = "id"
private const val STATE_NAME = "name"
private const val STATE_TEXT = "value"

@ExperimentalCoroutinesApi
class OverviewNoteViewModelTest {
    private lateinit var viewModel: OverviewNoteViewModel

    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val state: SavedStateHandle = SavedStateHandle()
    private val createUseCase: CreateUseCase = mockk(relaxed = true)
    private val loadByIdUseCase: LoadByIdUseCase = mockk()
    private val updateByIdUseCase: UpdateByIdUseCase = mockk(relaxed = true)
    private val deleteByIdUseCase: DeleteByIdUseCase = mockk()

    @Before
    fun setup() {
        viewModel = OverviewNoteViewModel(
            defaultDispatcher = defaultDispatcher,
            state = state,
            createUseCase = createUseCase,
            loadByIdUseCase = loadByIdUseCase,
            updateByIdUseCase = updateByIdUseCase,
            deleteByIdUseCase = deleteByIdUseCase
        )
    }

    @Test
    fun `load should fetch note and update state`() = runTest {
        val note = Note(
            id = 1L, name = "Test Note", text = "Test Content",
            textPreview = "preview",
            state = NoteState.Default,
            creationTime = ""
        )
        coEvery { loadByIdUseCase(1L) } returns note

        viewModel.load(1L)

        Assert.assertEquals(1L, state.get<Long>(STATE_ID))
        Assert.assertEquals("Test Note", state.get<String>(STATE_NAME))
        Assert.assertEquals("Test Content", state.get<String>(STATE_TEXT))
    }

    @Test
    fun `setName should update state`() {
        viewModel.setName("New Name")
        Assert.assertEquals("New Name", state.get<String>(STATE_NAME))
    }

    @Test
    fun `setText should update state`() {
        viewModel.setText("New Text")
        Assert.assertEquals("New Text", state.get<String>(STATE_TEXT))
    }

    @Test
    fun `save should call update when ID is set`() {
        state[STATE_ID] = 1L
        state[STATE_NAME] = "Updated Name"
        state[STATE_TEXT] = "Updated Text"

        viewModel.save()

        coVerify { updateByIdUseCase(1L, "Updated Name", "Updated Text") }
    }

    @Test
    fun `save should call create when ID is not set`() {
        state[STATE_ID] = -1L
        state[STATE_NAME] = "New Name"
        state[STATE_TEXT] = "New Text"

        viewModel.save()

        coVerify { createUseCase("New Name", "New Text") }
    }

    @Test
    fun `delete should call deleteByIdUseCase`() {
        state[STATE_ID] = 1L

        viewModel.delete()

        coVerify { deleteByIdUseCase(1L) }
    }
}
