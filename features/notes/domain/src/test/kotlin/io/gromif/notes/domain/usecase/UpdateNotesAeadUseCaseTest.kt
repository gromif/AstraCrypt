package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateNotesAeadUseCaseTest {
    private lateinit var updateNotesAeadUseCase: UpdateNotesAeadUseCase
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase = mockk()
    private val setAeadPreferenceUseCase: SetAeadPreferenceUseCase = mockk()
    private val repository: Repository = mockk()

    @Before
    fun setUp() {
        updateNotesAeadUseCase = UpdateNotesAeadUseCase(getAeadPreferenceUseCase, setAeadPreferenceUseCase, repository)
    }

    @Test
    fun shouldGetAeadPreference_thenCorrectlyUpdateRepositories() = runTest {
        val currentAeadMode: AeadMode = mockk()
        val targetAeadMode: AeadMode = mockk()

        coEvery { getAeadPreferenceUseCase() } returns currentAeadMode
        coJustRun { repository.changeAead(currentAeadMode, targetAeadMode) }
        coJustRun { setAeadPreferenceUseCase(targetAeadMode) }

        updateNotesAeadUseCase(targetAeadMode)

        coVerify(exactly = 1) { getAeadPreferenceUseCase() }
        coVerifyOrder {
            repository.changeAead(currentAeadMode, targetAeadMode)
            setAeadPreferenceUseCase(targetAeadMode)
        }
    }
}