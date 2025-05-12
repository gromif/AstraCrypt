package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoadByIdUseCaseTest {
    private lateinit var loadByIdUseCase: LoadByIdUseCase
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase = mockk()
    private val repository: Repository = mockk()

    @Before
    fun setUp() {
        loadByIdUseCase = LoadByIdUseCase(getAeadPreferenceUseCase, repository)
    }

    @Test
    fun shouldGetAeadPreference_thenGetNoteByIdFromRepository() = runTest {
        val aeadMode: AeadMode = mockk()
        val id: Long = 15
        val targetNote: Note = mockk()

        coEvery { getAeadPreferenceUseCase() } returns aeadMode
        coEvery { repository.getById(aeadMode, id) } returns targetNote

        val note = loadByIdUseCase(id)
        Assert.assertSame(targetNote, note)

        coVerify(exactly = 1) { getAeadPreferenceUseCase() }
        coVerify(exactly = 1) { repository.getById(aeadMode, id) }
    }
}