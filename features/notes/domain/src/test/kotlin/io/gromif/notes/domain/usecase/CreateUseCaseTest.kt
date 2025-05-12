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

class CreateUseCaseTest {
    private lateinit var createUseCase: CreateUseCase
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase = mockk()
    private val repository: Repository = mockk()

    @Before
    fun setUp() {
        createUseCase = CreateUseCase(getAeadPreferenceUseCase, repository)
    }

    @Test
    fun shouldGetAeadPreference_thenInsertInRepository() = runTest {
        val aeadMode: AeadMode = mockk()
        val name = "Note name"
        val text = "Lorem ipsum dolor sit amet"

        coEvery { getAeadPreferenceUseCase() } returns aeadMode
        coJustRun { repository.insert(aeadMode, name, text) }

        createUseCase(name, text)

        coVerify(exactly = 1) { getAeadPreferenceUseCase() }
        coVerify(exactly = 1) { repository.insert(aeadMode, name, text) }
    }
}