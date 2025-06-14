package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateByIdUseCaseTest {
    private lateinit var updateByIdUseCase: UpdateByIdUseCase
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase = mockk()
    private val repository: Repository = mockk()

    @Before
    fun setUp() {
        updateByIdUseCase = UpdateByIdUseCase(getAeadPreferenceUseCase, repository)
    }

    @Test
    fun shouldGetAeadPreference_thenUpdateRepository() = runTest {
        val aeadMode: AeadMode = mockk()
        val id: Long = 15
        val name = "Note name"
        val text = "Lorem ipsum dolor sit amet"

        coEvery { getAeadPreferenceUseCase() } returns aeadMode
        coJustRun { repository.update(aeadMode, id, name, text) }

        updateByIdUseCase(id, name, text)

        coVerify(exactly = 1) { getAeadPreferenceUseCase() }
        coVerify(exactly = 1) { repository.update(aeadMode, id, name, text) }
    }
}